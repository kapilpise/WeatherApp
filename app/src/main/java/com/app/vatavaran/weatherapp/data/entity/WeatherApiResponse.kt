package com.app.vatavaran.weatherapp.data.entity

data class WeatherApiResponse(
    val current: Current,
    val forecast: Forecast?,
    val location: Location?
)