package com.app.vatavaran.weatherapp.preference

import android.content.SharedPreferences
import com.app.vatavaran.weatherapp.utils.AppConstants
import javax.inject.Inject


class PreferenceUtil @Inject constructor(private val preferences: SharedPreferences) {

    fun saveCityListFromPref(arrayList: List<String>) {
        val citySet = arrayList.toSet()
        with(preferences.edit()) {
            putStringSet(AppConstants.KEY_CITY, citySet)
            apply()
        }
    }

    fun getCityListFromPref(): List<String> {
        val citySet = preferences.getStringSet(AppConstants.KEY_CITY, emptySet())
        val cityArrayList = arrayListOf<String>()
        if (citySet?.isEmpty()!!) {
            cityArrayList.add("New Delhi")
            cityArrayList.add("London")
            cityArrayList.add("Mumbai")
            cityArrayList.add("Nagpur")
            cityArrayList.add("Texas")
        }
        cityArrayList.addAll(citySet)
        return cityArrayList
    }

    fun clearPref() {
        with(preferences.edit()) {
            clear()
            apply()
        }
    }
}