plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "API_URL",
                "\"https://api-gateway-6tyymw4cxq-ew.a.run.app/\""
            )
        }
        debug {
            buildConfigField(
                "String",
                "API_URL",
                "\"https://api-gateway-6tyymw4cxq-ew.a.run.app/\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/LICENSE"
            excludes += "/META-INF/NOTICE*"
        }
    }
}

dependencies {
    implementation(`androidx-core-ktx`())
    implementation(`androidx-lifecycle-runtime-ktx`())
    implementation(`androidx-activity-compose`())
    implementation(platform(`androidx-compose-bom`()))
    implementation(`androidx-compose-ui`())
    implementation(`androidx-compose-ui-graphics`())
    implementation(`androidx-compose-ui-tooling-preview`())
    implementation(`androidx-compose-material3`())
    implementation(platform(`firebase-bom`()))
    implementation(`firebase-auth-ktx`())
    implementation(`play-services-auth`())
    implementation(`android-material`())
    implementation("com.google.firebase:firebase-messaging:23.3.1")
    testImplementation(junit())
    androidTestImplementation(`junit-ext`())
    androidTestImplementation(`espresso-core`())
    androidTestImplementation(`junit-compose`())
    debugImplementation(`compose-ui-test-tooling`())
    debugImplementation(`test-manifest`())
    implementation(`lifecycle-viewmodel-compose`())
    implementation(`runtime-compose`())
    implementation(`maps-compose-utils`())
    implementation(`navigation-compose`())
    implementation(`coil-compose`())
    implementation(`play-services-maps`())
    implementation(`play-services-location`())
    implementation(`maps-compose`())
    implementation(`maps-ktx`())
    implementation(`maps-utils-ktx`())
    implementation(`apollo-runtime`())
    implementation(`dialog-datetime`())
    implementation(`google-places`())
    testImplementation(`kotlinx-coroutines-test`())
    implementation(`firebase-firestore`())
    implementation("com.github.tehras:charts:0.2.4-alpha")
    implementation("com.github.a914-gowtham:compose-ratingbar:1.3.4")
    implementation("com.exyte:animated-navigation-bar:1.0.0")
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.27.0")

}