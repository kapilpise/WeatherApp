package com.app.vatavaran.weatherapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.vatavaran.weatherapp.utils.TestTags
import com.app.vatavaran.weatherapp.viewmodels.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddCityScreen(
    navigateToHomeScreen: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    var cityItems by remember {
        mutableStateOf((homeViewModel.lstPrefCities?.getCityListFromPref()!!))
    }
    var isDisplayAlert by remember {
        mutableStateOf(false)
    }
    var txtCityName by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            NavTopBar(modifier = Modifier.testTag(TestTags.ADD_CITY_TOPBAR),
                title = "City",
                canNavigateBack = true,
                navigateUp = {
                    navigateToHomeScreen()
                })
        },
        floatingActionButton = {
            AddCityBottomFloatingBtn(addItemToList = { newCity ->
                isDisplayAlert = true
            })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            cityItems.forEach { city ->
                run { CityItem(city) }
            }
        }
        if (isDisplayAlert) {
            AlertDialog(
                onDismissRequest = { isDisplayAlert = false },
                modifier = Modifier.testTag(TestTags.ADD_CITY_DIALOG),
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            if (txtCityName.isNotEmpty()) {
                                cityItems = cityItems + txtCityName
                                isDisplayAlert = false
                                homeViewModel.lstPrefCities!!.saveCityListFromPref(cityItems)
                            }
                        }) {
                            Text(text = "Add New City")
                        }
                        Button(modifier = Modifier.testTag(TestTags.ADD_CITY_DIALOG_BTN), onClick = {
                            txtCityName = ""
                            isDisplayAlert = false
                        }) {
                            Text(text = "Cancel")
                        }
                    }
                },
                title = {
                    Text(
                        "Add New City",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Column {
                        OutlinedTextField(value = txtCityName,
                            onValueChange = { txtCityName = it },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 18.sp),
                            modifier = Modifier
                                .padding(8.dp)
                                .testTag(TestTags.ADD_CITY_TEXT_FIELD),
                            label = { Text(text = "Enter City") })
                    }
                })
        }
    }
}

@Composable
fun AddCityBottomFloatingBtn(addItemToList: (newCity: String) -> Unit) {
    FloatingActionButton(
        onClick = {
            addItemToList("Pune")
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.surfaceVariant,
        shape = CircleShape,
        modifier = Modifier.testTag(TestTags.ADD_CITY_FAB)
    ) {
        Icon(Icons.Filled.Add, " floating action button.")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavTopBar(
    modifier: Modifier = Modifier,
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    actions: @Composable () -> Unit = {}
) {
    if (canNavigateBack) {
        TopAppBar(title = {
            Text(text = title)
        }, actions = { actions() }, navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "back"
                )
            }
        }, modifier = modifier
        )
    } else {
        TopAppBar(title = {
            Text(text = title)
        }, actions = { actions() }, modifier = modifier
        )
    }
}

@Composable
fun CityItem(city: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ), modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Row {
            Text(
                text = city,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
        }
    }
}