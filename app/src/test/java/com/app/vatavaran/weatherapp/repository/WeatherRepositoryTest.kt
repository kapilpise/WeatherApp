package com.app.vatavaran.weatherapp.repository

import com.app.vatavaran.weatherapp.data.WeatherForcastApiState
import com.app.vatavaran.weatherapp.data.datasource.WeatherDataSource
import com.app.vatavaran.weatherapp.data.entity.Condition
import com.app.vatavaran.weatherapp.data.entity.Current
import com.app.vatavaran.weatherapp.data.entity.WeatherApiResponse
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response


class WeatherRepositoryTest {

    @Mock
    lateinit var weatherDataSource: WeatherDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getCurrentWeather_Loading() = runTest {
        Mockito.`when`(weatherDataSource.getWeatherForcastApi("New Delhi")).thenReturn(
            Response.success(
                WeatherForcastApiState().weatherApiResponse
            )
        )
        val sut = WeatherRepository(weatherDataSource)
        val result = sut.fetchCurrentWeather("New Delhi")
        Assert.assertEquals(false, result.loading)
    }

    @Test
    fun getCurrentWeather_Error() = runTest {
        Mockito.`when`(weatherDataSource.getWeatherForcastApi("")).thenReturn(
            Response.error(
                500,
                "Error fetching data".toResponseBody()
            )
        )
        val sut = WeatherRepository(weatherDataSource)
        val result = sut.fetchCurrentWeather("")
        Assert.assertEquals("Error fetching data", result.error)
    }

    @Test
    fun getCurrentWeather_Success() = runTest {
        val current = Current(
            cloud = 1,
            wind_dir = "south",
            wind_kph = 1.0,
            temp_c = 23.4,
            temp_f = 0.0,
            last_updated_epoch = 1658522700,
            condition = Condition(
                text = "Partly cloudy",
                icon = "//cdn.weatherapi.com/weather/64x64/day/116.png"
            ),
            last_updated = "2022-07-22 16:45",
            feelslike_c = 23.3,
            feelslike_f = 23.3
        )
        val weatherApiResponse = WeatherApiResponse(current = current, null, null)
        Mockito.`when`(weatherDataSource.getWeatherForcastApi("")).thenReturn(
            Response.success(
                weatherApiResponse
            )
        )
        val sut = WeatherRepository(weatherDataSource)
        val result = sut.fetchCurrentWeather("New Delhi")
        Assert.assertEquals(1, result.weatherApiResponse?.current?.cloud)
    }

    @After
    fun tearDown() {
    }
}