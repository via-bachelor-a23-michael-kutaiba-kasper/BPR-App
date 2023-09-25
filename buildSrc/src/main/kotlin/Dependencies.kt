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
