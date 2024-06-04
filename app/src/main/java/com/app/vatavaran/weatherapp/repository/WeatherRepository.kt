package com.app.vatavaran.weatherapp.repository

import com.app.vatavaran.weatherapp.data.WeatherForcastApiState
import com.app.vatavaran.weatherapp.data.datasource.WeatherDataSource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherDataSource: WeatherDataSource
) {
    suspend fun fetchCurrentWeather(city: String): WeatherForcastApiState {
        return try {
            val response = weatherDataSource.getWeatherForcastApi(city)

            if (response.isSuccessful && response.body() != null) {
                WeatherForcastApiState(loading = false, response.body())
            } else {
                println(response.errorBody())
                WeatherForcastApiState(loading = false, response.body(), "Error fetching data")
            }

        } catch (exp: Exception) {
            WeatherForcastApiState(loading = false, null, "Something went wrong")
        }
    }
}
