plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // Implementacion de Serializacion paso 1 (como el docente)
    kotlin("plugin.serialization") version "2.0.21"

    // Room con KSP (como el docente)
    id("com.google.devtools.ksp")

    // Firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.marketplace"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.marketplace"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions { jvmTarget = "11" }

    buildFeatures { viewBinding = true }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Implementacion de Serializacion paso 2 (como el docente)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // ROOM (como el docente)
    implementation("androidx.room:room-runtime:2.8.3")
    implementation("androidx.room:room-ktx:2.8.3")
    ksp("androidx.room:room-compiler:2.8.3")

    // Coroutines (puedes dejar la tuya si tu docente la usa distinta)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    // Firebase (deja BOM + auth)
    implementation(platform("com.google.firebase:firebase-bom:34.6.0"))
    implementation("com.google.firebase:firebase-auth")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
