package com.app.vatavaran.weatherapp.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.app.vatavaran.weatherapp.data.entity.Current
import com.app.vatavaran.weatherapp.data.entity.Forecastday
import com.app.vatavaran.weatherapp.data.entity.Hour
import com.app.vatavaran.weatherapp.utils.TestTags
import com.app.vatavaran.weatherapp.viewmodels.HomeViewModel
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToAddCityScreen: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    val weatherForcastApiStateState by homeViewModel.weatherForcastApiState

    Scaffold(
        floatingActionButton = {
            BottomFloatingAB(navigateToAddCityScreen)
        },
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF85bf58))
                    .verticalScroll(state)
                    .testTag(TestTags.MAIN_COLUMN)
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                CityOrAreaCard(homeViewModel.lstPrefCities?.getCityListFromPref()!!, onCityChange = { city ->
                    homeViewModel.fetchWeatherForcast(city)
                })

                when {
                    weatherForcastApiStateState.loading -> {
                        LoaderView()
                    }

                    weatherForcastApiStateState.error != null ->
                        ErrorView(errMessage = "Error Fetching Data, Please try again")

                    else -> {
                        val data = weatherForcastApiStateState.weatherApiResponse
                        CurrentTemperature(data?.current)
                        HourlyTemperature(data?.forecast?.forecastday)
                        DailyTemperature(data?.forecast?.forecastday)
                        Spacer(modifier = Modifier.height(84.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun BottomFloatingAB(navigateToAddCityScreen: () -> Unit) {
    FloatingActionButton(
        onClick = { navigateToAddCityScreen() },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.surfaceVariant,
        shape = CircleShape,
        modifier = Modifier.testTag(TestTags.HOME_FAB)
    ) {
        Icon(Icons.Filled.Add, " floating action button.")
    }
}

@Composable
private fun CityOrAreaCard(listCities: List<String>, onCityChange: (city: String) -> Unit) {
    var iExpanded by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf(listCities.first()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = { iExpanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White, shape = RoundedCornerShape(38.dp),
                )
        ) {
            Text(
                text = selectedCity,
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
            )
            Icon(Icons.Default.ArrowDropDown, contentDescription = TestTags.HOME_BTN_ARROW)
        }
        DropdownMenu(
            iExpanded, onDismissRequest = { iExpanded = false }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            repeat(listCities.size) { index ->
                DropdownMenuItem(text = { Text(text = listCities.get(index)) }, onClick = {
                    selectedCity = listCities.get(index)
                    iExpanded = false
                    onCityChange(selectedCity)
                })
            }
        }
    }
}


@Composable
private fun CurrentTemperature(currentWeather: Current?) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        ) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${currentWeather?.temp_c}\u00B0c",
                modifier = Modifier
                    .padding(8.dp)
                    .testTag(TestTags.CURRENT_WEATHER_VIEW),
                fontSize = 48.sp,
                style = TextStyle(color = Color.DarkGray, fontWeight = FontWeight.Bold)
            )
            currentWeather?.condition?.icon?.let {

                Image(
                    painter = rememberAsyncImagePainter("https:$it"),
                    contentDescription = currentWeather.condition.text,
                    modifier = Modifier
                        .width(72.dp)
                        .height(72.dp)
                        .aspectRatio(1f)
                )
            }
            currentWeather?.condition?.text?.let {
                Text(
                    text = it, modifier = Modifier, fontSize = 26.sp, textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun HourlyTemperature(forecastday: List<Forecastday>?) {
    val hourlyTemperatures = forecastday?.get(0)?.hour
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ), modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag(TestTags.HOUR_CARD)

    ) {
        hourlyTemperatures?.let {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                // in the below line, we are setting data for each item of our listview.
                items(hourlyTemperatures) { hourlyTemperatureItem ->
                    // in the below line, we are creating a card for our list view item.
                    HourlyTemperatureItem(hourlyTemperatureItem)
                }
            }
        }
    }
}

@Composable()
private fun HourlyTemperatureItem(hourlyTemperatureItem: Hour) {
    Column(
        modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = hourlyTemperatureItem.time.split(" ")[1],
            modifier = Modifier,
            textAlign = TextAlign.Center,
            fontSize = 18.sp

        )
        Text(
            text = "${hourlyTemperatureItem.temp_c}°c",
            modifier = Modifier.padding(10.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            style = TextStyle(color = Color.DarkGray, fontWeight = FontWeight.Bold)
        )

        hourlyTemperatureItem.condition.icon.let {

            Image(
                painter = rememberAsyncImagePainter("https:$it"),
                contentDescription = hourlyTemperatureItem.condition.text,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .aspectRatio(1f)
            )
        }
        Text(
            text = hourlyTemperatureItem.condition.text,
            modifier = Modifier,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }
}

@Composable
private fun DailyTemperature(forecastday: List<Forecastday>?) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ), modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag(TestTags.DAILY_TEMP_CARD)

    ) {
        forecastday?.size?.let {
            repeat(it) {
                DailyTemperatureItem(forecastday.get(it))
            }
        }
    }
}

@Composable()
private fun DailyTemperatureItem(forecastOfDay: Forecastday) {

    val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.parse(forecastOfDay.date)
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    Row(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Text(
                    text = "${date.dayOfMonth} ${date.month.name.substring(0, 3)}",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 16.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Text(
                    text = date.dayOfWeek.name.substring(0, 3),
                    modifier = Modifier.padding(6.dp),
                    fontSize = 16.sp,
                )
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            forecastOfDay.day.condition.icon.let {

                Image(
                    painter = rememberAsyncImagePainter("https:$it"),
                    contentDescription = forecastOfDay.day.condition.text,
                    modifier = Modifier
                        .width(42.dp)
                        .height(42.dp)
                        .aspectRatio(1f)
                )
            }
            Text(
                text = forecastOfDay.day.condition.text,
                modifier = Modifier,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
        Column {
            Text(
                text = "${forecastOfDay.day.maxtemp_c}°c",
                modifier = Modifier.padding(8.dp),
                fontSize = 16.sp,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "${forecastOfDay.day.mintemp_c}°c", modifier = Modifier.padding(6.dp), fontSize = 16.sp
            )
        }
    }
}