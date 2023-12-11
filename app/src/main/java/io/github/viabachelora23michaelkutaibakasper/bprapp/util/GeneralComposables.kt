package io.github.viabachelora23michaelkutaibakasper.bprapp.util

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.BottomNavigationScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent.CreateEventViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun showSnackBar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    createEventViewModel: CreateEventViewModel,
    navController: NavHostController
) {
    scope.launch {
        val result = snackbarHostState.showSnackbar(
            message = "Event created successfully",
            actionLabel = "See event",
            // Defaults to SnackbarDuration.Short
            duration = SnackbarDuration.Short,
            withDismissAction = true
        )
        when (result) {
            SnackbarResult.ActionPerformed -> {
                Log.d(
                    "snackbarlog",
                    "id: ${createEventViewModel.createdEventId.value}"
                )
                navController.navigate("${BottomNavigationScreens.EventDetails.name}/${createEventViewModel.createdEventId.value}")
                createEventViewModel.eventCreated.value = false
            }

            SnackbarResult.Dismissed -> {
                createEventViewModel.eventCreated.value = false
            }

        }

    }
}