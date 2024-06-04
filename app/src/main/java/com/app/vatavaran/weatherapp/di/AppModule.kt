package com.app.vatavaran.weatherapp.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.app.vatavaran.weatherapp.data.api.ApiService
import com.app.vatavaran.weatherapp.data.datasource.WeatherDataSource
import com.app.vatavaran.weatherapp.data.datasource.WeatherDataSourceImpl
import com.app.vatavaran.weatherapp.repository.WeatherRepository
import com.app.vatavaran.weatherapp.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit {
        val httpInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpInterceptor)
        }

        httpClient.apply {
            readTimeout(30, TimeUnit.SECONDS)
        }

        return Retrofit
            .Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherDataSource(apiService: ApiService): WeatherDataSource {
        return WeatherDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherDataSource: WeatherDataSource): WeatherRepository {
        return WeatherRepository(weatherDataSource)
    }

    @Provides
    @Singleton
    fun provideSharedPreference(context: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}