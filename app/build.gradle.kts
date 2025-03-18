plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
    id("kotlin-kapt")



}

android {
    namespace = "com.example.mycinema"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.mycinema"
        minSdk = 26
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
}

dependencies {

    val room_version = "2.6.1"

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-ktx:2.6.1")
    implementation("com.google.android.material:material:1.12.0-rc01")
    implementation("com.google.android.material:material:1.2.1")
    implementation ("androidx.recyclerview:recyclerview:1.1.0")
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:3.12.6")
    //Dagger
    implementation ("com.google.dagger:dagger:2.48")
    kapt ("com.google.dagger:dagger-compiler:2.48")
}