package io.github.viabachelora23michaelkutaibakasper.bprapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in.GoogleAuthUIClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.MapView
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.profile.ProfileScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.profile.ProfileViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.theme.BPRAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    val googleAuthUiClient by lazy {
        GoogleAuthUIClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    enum class Screens() {
        Map,
        Recommendations,
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

    @OptIn(ExperimentalMaterial3Api::class)
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

                    val navController: NavHostController = rememberNavController()

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
                                        label = { Text("Recomm.") },
                                        selected = (selectedIndex.intValue == 1),
                                        onClick = {
                                            selectedIndex.intValue =
                                                1; navController.navigate(MainActivity.Screens.Recommendations.name)
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = Icons.Filled.ThumbUp,
                                                contentDescription = "Achievements",
                                                tint = Color.Black,
                                            )
                                        })
                                    NavigationBarItem(
                                        label = { Text("Medals") },
                                        selected = (selectedIndex.intValue == 2),
                                        onClick = {
                                            selectedIndex.intValue =
                                                2; navController.navigate(MainActivity.Screens.Achievements.name)
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
                                        selected = (selectedIndex.intValue == 3),
                                        onClick = {
                                            selectedIndex.intValue = 3;
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
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = MainActivity.Screens.Map.name,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(MainActivity.Screens.Map.name) {
                                MapView().Map(navController = navController)
                            }
                            composable(MainActivity.Screens.Recommendations.name) {
                                Text(text = "Recommendations")
                            }
                            composable(MainActivity.Screens.Achievements.name) {
                                Text(text = "Achievements")
                            }
                            composable(MainActivity.Screens.Profile.name) {
                                val viewModel = viewModel<ProfileViewModel>()
                                val state by viewModel.state.collectAsStateWithLifecycle()
                                LaunchedEffect(key1 = Unit) {
                                    if (googleAuthUiClient.getSignedInUser() != null) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Signed in",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                                val launcher = rememberLauncherForActivityResult(
                                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                                    onResult = { result ->
                                        if (result.resultCode == RESULT_OK) {
                                            lifecycleScope.launch {
                                                val signInResult =
                                                    googleAuthUiClient.signInWithIntent(
                                                        intent = result.data ?: return@launch
                                                    )
                                                viewModel.onSignInResult(signInResult)
                                            }
                                        }
                                    }
                                )
                                LaunchedEffect(key1 = state.isSignInSuccessful) {
                                    if (state.isSignInSuccessful) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Sign in successful",
                                            Toast.LENGTH_LONG
                                        ).show()

                                      //  navController.navigate("profile")
                                       // viewModel.resetState()
                                    }
                                }
                                ProfileScreen(
                                    state = state,
                                    onSignInClick = {
                                        lifecycleScope.launch {
                                            val signInIntentSender = googleAuthUiClient.signIn()
                                            launcher.launch(
                                                IntentSenderRequest.Builder(
                                                    signInIntentSender ?: return@launch
                                                ).build()
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BPRAppTheme {
        MainScreen()
    }
}