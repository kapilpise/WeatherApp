package com.app.vatavaran.weatherapp.data.entity

data class Current(
    val cloud: Int,
    val condition: Condition,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val last_updated: String,
    val last_updated_epoch: Int,
    val temp_c: Double,
    val temp_f: Double,
    val wind_dir: String,
    val wind_kph: Double
)