package com.app.vatavaran.weatherapp.data

import com.app.vatavaran.weatherapp.data.entity.WeatherApiResponse

data class WeatherForcastApiState(
    val loading: Boolean = true,
    val weatherApiResponse: WeatherApiResponse? = null,
    val error: String? = null
)
