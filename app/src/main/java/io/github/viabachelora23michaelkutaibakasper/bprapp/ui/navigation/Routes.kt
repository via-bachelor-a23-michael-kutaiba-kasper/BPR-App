package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation

import androidx.navigation.NavController


fun navigateTo(screen: String, navController: NavController) {
    navController.navigate(screen)
}


enum class BottomNavigationScreens {
    Map,
    Recommendations,
    Achievements,
    Profile,
    EventDetails
}

enum class CreateEventScreens {
    Title,
    Location,
    DateAndTime,
    Details,
    Images,
    InviteFriends,
    EventSummary
}