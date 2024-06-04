package com.app.vatavaran.weatherapp.navigation

sealed class ScreenRoutes(val route: String) {
    data object HomeScreen : ScreenRoutes("homescreen")
    data object AddCityScreen : ScreenRoutes("addcityscreen")
}