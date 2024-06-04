package com.app.vatavaran.weatherapp.data.api


import com.app.vatavaran.weatherapp.data.entity.WeatherApiResponse
import com.app.vatavaran.weatherapp.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("forecast.json")
    suspend fun getWeatherForcastApi(
        @Query("q") city: String,
        @Query("days") days: Int = AppConstants.NUM_DAYS,
        @Query("key") key: String = AppConstants.API_KEY
    ): Response<WeatherApiResponse>
}