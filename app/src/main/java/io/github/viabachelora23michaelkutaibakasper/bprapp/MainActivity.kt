package io.github.viabachelora23michaelkutaibakasper.bprapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.MapListView
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.MapView
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.theme.BPRAppTheme

class MainActivity : ComponentActivity() {
    enum class Screens() {
        Map,
        Achievements,
        Profile
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {

            }
        }

    private fun askPermissions() = when {
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED -> {

        }

        else -> {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)



        askPermissions()
        setContent {
            BPRAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    MainScreen()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()
    var presses by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                        }
                    }

                },
                title = {
                    Text("VibeVerse")
                }
            )
        },
        bottomBar = {
            val selectedIndex = remember { mutableIntStateOf(0) }
            NavigationBar(
                contentColor = MaterialTheme.colorScheme.secondary,
                content = {
                    NavigationBarItem(
                        label = { Text("Home") },
                        selected = (selectedIndex.intValue == 0),
                        onClick = {
                            selectedIndex.intValue = 0;
                            navController.navigate(MainActivity.Screens.Map.name)
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Map",
                                tint = Color.Black,
                            )
                        })
                    NavigationBarItem(
                        label = { Text("Achievements") },
                        selected = (selectedIndex.intValue == 1),
                        onClick = {
                            selectedIndex.intValue =
                                1; navController.navigate(MainActivity.Screens.Achievements.name)
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Achievements",
                                tint = Color.Black,
                            )
                        })
                    NavigationBarItem(
                        label = { Text("Profile") },
                        selected = (selectedIndex.intValue == 2),
                        onClick = {
                            selectedIndex.intValue = 2;
                            navController.navigate(MainActivity.Screens.Profile.name)
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Profile",
                                tint = Color.Black,
                            )
                        })
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                presses++
            }) {
                Column {
                    Icon(Icons.Default.Build, contentDescription = "Add")
                    Text(text = presses.toString())
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = MainActivity.Screens.Map.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(MainActivity.Screens.Map.name) {
                MapView().Map()
            }
            composable(MainActivity.Screens.Achievements.name) {
                MapListView().EventList()
            }
            composable(MainActivity.Screens.Profile.name) {
                Text(text = "Profile")
            }

            /* Column(
                 modifier = Modifier
                     .padding(innerPadding),
                 verticalArrangement = Arrangement.spacedBy(16.dp),
             ) {
                 val MapListView = MapView()
                 MapListView.Map()
                 // MapListView.EventList()
                 //  CountryList()
                 // CountrySpecific(code ="DK")
             }*/
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BPRAppTheme {
        MainScreen()
    }
}