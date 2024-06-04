package com.app.vatavaran.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.vatavaran.weatherapp.screens.AddCityScreen
import com.app.vatavaran.weatherapp.screens.HomeScreen

@Composable
fun WeatherAppNav(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = ScreenRoutes.HomeScreen.route) {
        composable(route = ScreenRoutes.HomeScreen.route) {
            HomeScreen(
                navigateToAddCityScreen = {
                    navHostController.navigate(ScreenRoutes.AddCityScreen.route)
                })
        }
        composable(route = ScreenRoutes.AddCityScreen.route) {
            AddCityScreen(
                navigateToHomeScreen = {
                    navHostController.navigate(ScreenRoutes.HomeScreen.route)
                })
        }
    }
}