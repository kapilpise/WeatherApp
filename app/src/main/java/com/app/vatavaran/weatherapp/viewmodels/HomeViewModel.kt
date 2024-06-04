package com.app.vatavaran.weatherapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.vatavaran.weatherapp.data.WeatherForcastApiState
import com.app.vatavaran.weatherapp.preference.PreferenceUtil
import com.app.vatavaran.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    prefUtil: PreferenceUtil?
) : ViewModel() {
    private val _weatherForcastApiState = mutableStateOf(WeatherForcastApiState())
    val weatherForcastApiState: State<WeatherForcastApiState> = _weatherForcastApiState
    val lstPrefCities = prefUtil

    init {
        if (lstPrefCities?.getCityListFromPref()!!.isNotEmpty()) {
            fetchWeatherForcast(lstPrefCities.getCityListFromPref().first())
        }
    }

    fun fetchWeatherForcast(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = weatherRepository.fetchCurrentWeather(city)
                _weatherForcastApiState.value = _weatherForcastApiState.value.copy(
                    loading = false, weatherApiResponse = response.weatherApiResponse, error = null
                )
            } catch (exp: Exception) {
                _weatherForcastApiState.value = _weatherForcastApiState.value.copy(
                    loading = false, error = "Err fetching categories ${exp.message}"
                )
            }
        }
    }
}