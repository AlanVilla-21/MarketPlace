plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

   // id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.marketplace"
    compileSdk {
        version = release(36)
    }

     buildFeatures {
        viewBinding = true
    }

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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    // serializacion
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    //ROOM
    implementation("androidx.room:room-runtime:2.6.0-alpha01")
    implementation("androidx.room:room-ktx:2.6.0-alpha01")
    kapt("androidx.room:room-compiler:2.6.0")
    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.6.0"))
    implementation("com.google.firebase:firebase-auth-ktx") // This works


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation(libs.firebase.auth.ktx)
    implementation(platform(libs.firebase.bom))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}