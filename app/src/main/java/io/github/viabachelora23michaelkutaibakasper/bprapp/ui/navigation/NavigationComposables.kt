@file:OptIn(ExperimentalFoundationApi::class)

package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.achievements.AchievementsScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.achievements.AchievementsViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent.CreateEventDateAndTimeScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent.CreateEventDetailsScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent.CreateEventImagesScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent.CreateEventLocationScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent.CreateEventTitleAndDescriptionScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent.CreateEventViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent.EventSummaryScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.eventdetails.EventDetailsScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.eventdetails.EventDetailsViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.map.Map
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.map.MapViewViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.profile.ProfileScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.profile.ProfileViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.recommendations.RecommendationsScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.recommendations.RecommendationsViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.localDateTimeToUTCLocalDateTime
import java.time.LocalDateTime


@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
 fun NavigationHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    mapViewModel: MapViewViewModel,
    recommendationsViewModel: RecommendationsViewModel,
    achievementsViewModel: AchievementsViewModel,
    profileViewModel: ProfileViewModel,
    createEventViewModel: CreateEventViewModel,
    eventDetailsViewModel: EventDetailsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationScreens.Map.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(BottomNavigationScreens.Map.name) {
            mapViewModel.getEvents(
                from = localDateTimeToUTCLocalDateTime(
                    LocalDateTime.now()
                ).toString()
            )
            Map(navController = navController, viewModel = mapViewModel)
        }
        composable(BottomNavigationScreens.Recommendations.name) {
            RecommendationsScreen(
                recommendationsViewModel,
                navController = navController
            )
        }
        composable(BottomNavigationScreens.Achievements.name) {
            AchievementsScreen(viewModel = achievementsViewModel)
        }
        composable(BottomNavigationScreens.Profile.name) {
            ProfileScreen(navController, profileViewModel)
        }
        composable(CreateEventScreens.Title.name) {
            CreateEventTitleAndDescriptionScreen(
                navController = navController, viewModel = createEventViewModel
            )
        }
        composable(CreateEventScreens.Location.name) {
            CreateEventLocationScreen(
                navController = navController, viewModel = createEventViewModel
            )
        }
        composable(CreateEventScreens.DateAndTime.name) {
            CreateEventDateAndTimeScreen(
                navController = navController, viewModel = createEventViewModel
            )
        }
        composable(CreateEventScreens.Details.name) {
            CreateEventDetailsScreen(
                navController = navController, viewModel = createEventViewModel
            )
        }
        composable(CreateEventScreens.Images.name) {
            CreateEventImagesScreen(navController = navController)
        }
        composable(
            "${BottomNavigationScreens.EventDetails.name}/{eventId}",
            arguments = listOf(navArgument("eventId") {
                type = NavType.IntType
            })
        ) {
            val param = it.arguments?.getInt("eventId")
            param?.let { it1 ->
                eventDetailsViewModel.getEvent(it1)
                EventDetailsScreen(
                    navController = navController,
                    viewModel = eventDetailsViewModel,
                    param = it1
                )
            }
        }
        composable(CreateEventScreens.EventSummary.name) {
            EventSummaryScreen(
                navController = navController, viewModel = createEventViewModel
            )
        }
    }
}