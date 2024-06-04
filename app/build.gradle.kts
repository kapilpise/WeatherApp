plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.app.vatavaran.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.vatavaran.weatherapp"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        testInstrumentationRunner = "com.app.vatavaran.weatherapp.HiltTestRunner"
        testInstrumentationRunner = "com.app.vatavaran.weatherapp.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
// ViewModel
    val lifecycle_version = "2.8.1"
    val arch_version = "2.2.0"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.2.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    implementation("com.squareup.retrofit2:retrofit:2.7.2")
    implementation("com.squareup.retrofit2:converter-gson:2.7.2")
    implementation("com.squareup.okhttp3:okhttp:3.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    val nav_version = "2.7.7"

    implementation("androidx.navigation:navigation-compose:$nav_version")
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.7")
    implementation("androidx.compose.ui:ui:1.6.0-alpha06")
    implementation("androidx.compose.ui:ui-graphics:1.6.0-alpha06")
    implementation("androidx.compose.material3:material3:1.2.0-alpha08")
    implementation("androidx.core:core-splashscreen:1.0.0-beta02")

    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    // ...with Kotlin.
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44")
    // ...with Java.
    androidTestAnnotationProcessor("com.google.dagger:hilt-android-compiler:2.44")

    // Optional -- Mockito framework
    testImplementation("org.mockito:mockito-core:5.2.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    // Optional -- Mockk framework
    testImplementation ("io.mockk:mockk:1.13.11")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}