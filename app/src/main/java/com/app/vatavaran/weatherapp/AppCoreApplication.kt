package com.app.vatavaran.weatherapp

import android.app.Application

open class AppCoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        println("App core application class")
    }
}
