plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.miniproject1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.miniproject1"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {

    implementation("com.mapbox.extension:maps-compose:11.0.0")
    implementation("dev.shreyaspatil.permission-flow:permission-flow-compose:1.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("com.mapbox.mapboxsdk:mapbox-android-plugin-annotation-v9:0.9.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.mapbox.mapboxsdk:mapbox-android-core:5.0.2")
    implementation("com.mapbox.mapboxsdk:mapbox-android-core:5.0.2")
    implementation("com.mapbox.extension:maps-compose:11.0.0")
    implementation("com.mapbox.maps:android:10.9.1")
    implementation("com.mapbox.mapboxsdk:mapbox-android-plugin-annotation-v9:0.9.0")
    implementation("dev.shreyaspatil.permission-flow:permission-flow-compose:1.2.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")


    implementation("androidx.preference:preference:1.2.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    ksp("androidx.room:room-compiler:2.5.2")
    implementation("androidx.room:room-ktx:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}