
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

    id("com.apollographql.apollo3").version("3.8.2")
    id("com.google.gms.google-services")
}

apollo {
    service("service") {
        packageName.set("io.github.viabachelora23michaelkutaibakasper.bprapp")
    }
}
kotlin {
    jvmToolchain(17)
}
android {
    namespace = "io.github.viabachelora23michaelkutaibakasper.bprapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.github.viabachelora23michaelkutaibakasper.bprapp"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        kotlinOptions {
            jvmTarget = "17"
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

dependencies {
    // Android
    implementation(`androidx-core-ktx`())
    implementation(`androidx-lifecycle-runtime-ktx`())
    implementation(`androidx-activity-compose`())

    // Compose
    implementation(platform(`androidx-compose-bom`()))
    implementation(`androidx-compose-ui`())
    implementation(`androidx-compose-ui-graphics`())
    implementation(`androidx-compose-ui-tooling-preview`())
    implementation(`androidx-compose-material3`())
    implementation(platform("com.google.firebase:firebase-bom:32.4.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    // TODO: Move rest of dependencies into Versions.kt and Dependencies.kt
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation(`lifecycle-viewmodel-compose`())
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    implementation(`navigation-compose`())

    // Google maps
    implementation(`play-services-maps`())
    implementation(`play-services-location`())
    // Google maps for compose
    implementation(`maps-compose`())

    // KTX for the Maps SDK for Android
    implementation(`maps-ktx`())
    // KTX for the Maps SDK for Android Utility Library
    implementation(`maps-utils-ktx`())

    //apollo
    implementation(`apollo-runtime`())

}