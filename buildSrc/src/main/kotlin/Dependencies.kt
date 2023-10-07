import org.gradle.api.artifacts.dsl.DependencyHandler
import Versions

// Android
fun DependencyHandler.`androidx-core-ktx` (version:String = Versions.ANDROIDX_CORE_KTX)= "androidx.core:core-ktx:${version}"
fun DependencyHandler.`androidx-lifecycle-runtime-ktx`(version:String = Versions.ANDROIDX_LIFECYCLE_RUNTIME_KTX) = "androidx.lifecycle:lifecycle-runtime-ktx:${version}"

fun DependencyHandler.`androidx-activity-compose`(version: String = Versions.ANDROIDX_ACTIVITY_COMPOSE) = "androidx.activity:activity-compose:${version}"
fun DependencyHandler.`androidx-compose-bom`(version: String = Versions.ANDROIDX_COMPOSE_BOM) = "androidx.compose:compose-bom:${version}"
fun DependencyHandler.`androidx-compose-ui`(version: String = Versions.ANDROIDX_COMPOSE_UI) = "androidx.compose.ui:ui:${version}"
fun DependencyHandler.`androidx-compose-ui-graphics`(version: String = Versions.ANDROIDX_COMPOSE_UI_GRAPHICS) = "androidx.compose.ui:ui-graphics:${version}"
fun DependencyHandler.`androidx-compose-ui-tooling-preview`(version: String = Versions.ANDROIDX_COMPOSE_UI_TOOLING_PREVIEW) = "androidx.compose.ui:ui-tooling-preview:${version}"
fun DependencyHandler.`androidx-compose-material3`(version:String = Versions.ANDROIDX_COMPOSE_MATERIAL3) = "androidx.compose.material3:material3:${version}"

fun DependencyHandler.`secrets-gradle-plugin`(version:String = Versions.GRADLE_SECRETS_PLUGINS) = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${version}"
fun DependencyHandler.`play-services-maps`(version:String = Versions.PLAY_SERVICES_MAPS) = "com.google.android.gms:play-services-maps:${version}"
fun DependencyHandler.`play-services-location`(version:String = Versions.PLAY_SERVICES_LOCATION) = "com.google.android.gms:play-services-location:${version}"
fun DependencyHandler.`maps-compose`(version:String = Versions.MAPS_COMPOSE) = "com.google.maps.android:maps-compose:${version}"
fun DependencyHandler.`maps-ktx`(version:String = Versions.MAPS_KTX) = "com.google.maps.android:maps-ktx:${version}"
fun DependencyHandler.`maps-utils-ktx`(version:String = Versions.MAPS_UTILS_KTX) = "com.google.maps.android:maps-utils-ktx:${version}"

// Serialization
fun DependencyHandler.`gson`(version: String = Versions.GSON) = "com.google.code.gson:gson:$version"
