plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.maruf.pokemon"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.maruf.pokemon"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
    dataBinding {
        enable = true
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //carousel Recyclerview
    implementation(libs.carouselrecyclerview)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Image Loading library Coil
    implementation(libs.coil)

    //glide image loader
    implementation(libs.glide)

    // Gson
    implementation(libs.gson)

    //Dagger - Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)

    //lottie
    implementation(libs.lottie)

}