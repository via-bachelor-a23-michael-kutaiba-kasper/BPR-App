import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.`androidx-core-ktx`(version: String = Versions.ANDROIDX_CORE_KTX) =
    "androidx.core:core-ktx:${version}"

fun DependencyHandler.`androidx-lifecycle-runtime-ktx`(version: String = Versions.ANDROIDX_LIFECYCLE_RUNTIME_KTX) =
    "androidx.lifecycle:lifecycle-runtime-ktx:${version}"

fun DependencyHandler.`androidx-activity-compose`(version: String = Versions.ANDROIDX_ACTIVITY_COMPOSE) =
    "androidx.activity:activity-compose:${version}"

fun DependencyHandler.`androidx-compose-bom`(version: String = Versions.ANDROIDX_COMPOSE_BOM) =
    "androidx.compose:compose-bom:${version}"

fun DependencyHandler.`androidx-compose-ui`(version: String = Versions.ANDROIDX_COMPOSE_UI) =
    "androidx.compose.ui:ui:${version}"

fun DependencyHandler.`androidx-compose-ui-graphics`(version: String = Versions.ANDROIDX_COMPOSE_UI_GRAPHICS) =
    "androidx.compose.ui:ui-graphics:${version}"

fun DependencyHandler.`androidx-compose-ui-tooling-preview`(version: String = Versions.ANDROIDX_COMPOSE_UI_TOOLING_PREVIEW) =
    "androidx.compose.ui:ui-tooling-preview:${version}"

fun DependencyHandler.`androidx-compose-material3`(version: String = Versions.ANDROIDX_COMPOSE_MATERIAL3) =
    "androidx.compose.material3:material3:${version}"

fun DependencyHandler.`secrets-gradle-plugin`(version: String = Versions.GRADLE_SECRETS_PLUGINS) =
    "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${version}"

fun DependencyHandler.`play-services-maps`(version: String = Versions.PLAY_SERVICES_MAPS) =
    "com.google.android.gms:play-services-maps:${version}"

fun DependencyHandler.`play-services-location`(version: String = Versions.PLAY_SERVICES_LOCATION) =
    "com.google.android.gms:play-services-location:${version}"

fun DependencyHandler.`maps-compose`(version: String = Versions.MAPS_COMPOSE) =
    "com.google.maps.android:maps-compose:${version}"

fun DependencyHandler.`maps-ktx`(version: String = Versions.MAPS_KTX) =
    "com.google.maps.android:maps-ktx:${version}"

fun DependencyHandler.`maps-utils-ktx`(version: String = Versions.MAPS_UTILS_KTX) =
    "com.google.maps.android:maps-utils-ktx:${version}"

fun DependencyHandler.`apollo-runtime`(version: String = Versions.APOLLO_RUNTIME) =
    "com.apollographql.apollo3:apollo-runtime:${version}"

fun DependencyHandler.`build-gradle`(version: String = Versions.GRADLE_BUILD) =
    "com.android.tools.build:gradle:${version}"

fun DependencyHandler.`kotlin-gradle-plugin`(version: String = Versions.KOTLIN_GRADLE_PLUGIN) =
    "org.jetbrains.kotlin:kotlin-gradle-plugin:${version}"

fun DependencyHandler.`lifecycle-viewmodel-compose`(version: String = Versions.LIFECYCLE_VIEWMODEL_COMPOSE) =
    "androidx.lifecycle:lifecycle-viewmodel-compose:${version}"

fun DependencyHandler.`navigation-compose`(version: String = Versions.NAVIGATION_COMPOSE) =
    "androidx.navigation:navigation-compose:${version}"

fun DependencyHandler.`firebase-bom`(version: String = Versions.FIREBASE_BOM) =
    "com.google.firebase:firebase-bom:${version}"

fun DependencyHandler.`firebase-auth-ktx`() = "com.google.firebase:firebase-auth-ktx"
fun DependencyHandler.`play-services-auth`(version: String = Versions.PLAY_SERVICES_AUTH) =
    "com.google.android.gms:play-services-auth:${version}"

fun DependencyHandler.`coil-compose`(version: String = Versions.COIL_COMPOSE) =
    "io.coil-kt:coil-compose:${version}"

fun DependencyHandler.`google-places`(version: String = Versions.PLACES) =
    "com.google.android.libraries.places:places:${version}"

fun DependencyHandler.`dialog-datetime`(version: String = Versions.DATETIME) =
    "io.github.vanpra.compose-material-dialogs:datetime:${version}"

fun DependencyHandler.`maps-compose-utils`(version: String = Versions.MAPS_COMPOSE_UTILS) =
    "com.google.maps.android:maps-compose-utils:${version}"

fun DependencyHandler.`runtime-compose`(version: String = Versions.RUNTIME_COMPOSE) =
    "androidx.lifecycle:lifecycle-runtime-compose:${version}"

fun DependencyHandler.`compose-ui-test-tooling`() = "androidx.compose.ui:ui-tooling:"
fun DependencyHandler.`test-manifest`() = "androidx.compose.ui:ui-test-manifest:"
fun DependencyHandler.`junit-compose`(version: String = Versions.JUNIT_COMPOSE) =
    "androidx.compose.ui:ui-test-junit4:${version}"

fun DependencyHandler.`espresso-core`(version: String = Versions.ESPRESSO) =
    "androidx.test.espresso:espresso-core:${version}"

fun DependencyHandler.`junit-ext`(version: String = Versions.JUNIT_EXT) =
    "androidx.test.ext:junit:${version}"

fun DependencyHandler.`junit`(version: String = Versions.JUNIT) = "junit:junit:${version}"
fun DependencyHandler.`android-material`(version: String = Versions.ANDROID_MATERIAL) =
    "com.google.android.material:material:${version}"

fun DependencyHandler.`kotlinx-coroutines-test`(version: String = Versions.KOTLINX_COROUTINES_TEST) =
    "org.jetbrains.kotlinx:kotlinx-coroutines-test:${version}"

fun DependencyHandler.`firebase-firestore`() = "com.google.firebase:firebase-firestore"
fun DependencyHandler.`firebase-messaging`(version: String = Versions.FIREBASE_MESSAGING) =
    "com.google.firebase:firebase-messaging:${version}"

fun DependencyHandler.charts(version: String = "0.2.4-alpha") =
    "com.github.tehras:charts:${version}"

fun DependencyHandler.`compose-ratingbar`(version: String = "1.3.4") =
    "com.github.a914-gowtham:compose-ratingbar:${version}"

fun DependencyHandler.`animated-navigation-bar`(version: String = "1.0.0") =
    "com.exyte:animated-navigation-bar:${version}"

fun DependencyHandler.`swipe-refresh`(version: String = "0.27.0") =
    "com.google.accompanist:accompanist-swiperefresh:${version}"

fun DependencyHandler.`play-services`(): String {
    return "com.google.gms:google-services:${Versions.PLAY_SERVICES}"
}