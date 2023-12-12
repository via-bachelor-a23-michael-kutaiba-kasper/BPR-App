package io.github.viabachelora23michaelkutaibakasper.bprapp

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.BottomBar
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.NavigationHost
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.achievements.AchievementsViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent.CreateEventViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.eventdetails.EventDetailsViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.map.MapViewViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.profile.ProfileViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.recommendations.RecommendationsViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.theme.AppTheme
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.showSnackBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun VibeVerseApp() {
    AppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            val createEventViewModel: CreateEventViewModel = viewModel()
            val eventDetailsViewModel: EventDetailsViewModel = viewModel()
            val profileViewModel: ProfileViewModel = viewModel()
            val mapViewModel: MapViewViewModel = viewModel()
            val recommendationsViewModel: RecommendationsViewModel = viewModel()
            val achievementsViewModel: AchievementsViewModel = viewModel()
            val navController: NavHostController = rememberNavController()
            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }


            Scaffold(snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }, topBar = {
                TopAppBar(navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                        }
                    }
                }, title = {
                    Text("VibeVerse")
                })
            }, bottomBar = {
                BottomBar(navController)

            }) { innerPadding ->
                if (createEventViewModel.eventCreated.value) {
                    Log.d("MainActivity", "onCreate: event created")
                    showSnackBar(
                        scope,
                        snackbarHostState,
                        createEventViewModel,
                        navController
                    )
                }

                NavigationHost(
                    navController,
                    innerPadding,
                    mapViewModel,
                    recommendationsViewModel,
                    achievementsViewModel,
                    profileViewModel,
                    createEventViewModel,
                    eventDetailsViewModel
                )
            }
        }
    }
}