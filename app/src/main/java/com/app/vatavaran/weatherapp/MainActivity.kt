package com.app.vatavaran.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.app.vatavaran.weatherapp.navigation.WeatherAppNav
import com.app.vatavaran.weatherapp.ui.theme.VatavaranTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            VatavaranTheme {
                Scaffold(topBar = {}, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navigationController = rememberNavController()
                    WeatherAppNav(navigationController)
                }
            }
        }
    }
}