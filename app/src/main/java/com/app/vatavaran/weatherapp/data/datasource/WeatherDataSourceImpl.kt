package com.app.vatavaran.weatherapp.data.datasource

import com.app.vatavaran.weatherapp.data.api.ApiService
import com.app.vatavaran.weatherapp.data.entity.WeatherApiResponse
import retrofit2.Response
import javax.inject.Inject

class WeatherDataSourceImpl @Inject constructor(private val apiService: ApiService) : WeatherDataSource {
    override suspend fun getWeatherForcastApi(q: String): Response<WeatherApiResponse> {
        return apiService.getWeatherForcastApi(q)
    }
}