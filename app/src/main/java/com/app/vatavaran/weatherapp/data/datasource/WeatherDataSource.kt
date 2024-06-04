package com.app.vatavaran.weatherapp.data.datasource

import com.app.vatavaran.weatherapp.data.entity.WeatherApiResponse
import retrofit2.Response

interface WeatherDataSource {
    suspend fun getWeatherForcastApi(
        q: String,
    ): Response<WeatherApiResponse>
}