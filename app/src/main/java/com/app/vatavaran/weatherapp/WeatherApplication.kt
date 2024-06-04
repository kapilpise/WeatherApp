package com.app.vatavaran.weatherapp

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : AppCoreApplication() {
    override fun onCreate() {
        super.onCreate()
        println("WeatherApplication class created")
    }
}