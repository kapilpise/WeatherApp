package com.app.vatavaran.weatherapp.data.entity

data class Hour(
    val condition: Condition,
    val temp_c: Double,
    val temp_f: Double,
    val time: String,
    val time_epoch: Int
)