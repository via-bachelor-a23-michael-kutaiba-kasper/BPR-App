package io.github.viabachelora23michaelkutaibakasper.bprapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.AppTheme
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.utils.noRippleClickable
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in.AuthenticationClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in.IAuthenticationClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.BottomNavigationScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.CreateEventScreens
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
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.theme.BPRAppTheme
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.localDateTimeToUTCLocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@ExperimentalLayoutApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {


    private val _usemylocation = MutableStateFlow(false)
    var usemylocation = _usemylocation.asStateFlow()
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            _usemylocation.value = true
        } else {
            _usemylocation.value = false
        }
    }

    private fun askPermissions() = when {
        ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED -> {

            _usemylocation.value = true
        }

        else -> {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)



        askPermissions()
        askNotificationPermission()
        setContent {
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
                    var selectedIndex = remember { mutableIntStateOf(2) }

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
                        AnimatedNavigationBar(
                            selectedIndex = selectedIndex.value,
                            modifier = Modifier
                                .height(68.dp),

                            ballAnimation = Straight(
                                tween(400)
                            ),
                            indentAnimation = Height(tween(500)),
                            ballColor = MaterialTheme.colorScheme.primary,
                            barColor = MaterialTheme.colorScheme.primary
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .noRippleClickable {
                                        selectedIndex.value = 0

                                    },
                                contentAlignment = Alignment.Center
                            )
                            {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Bottom App icon",
                                    tint = if (selectedIndex.value == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .noRippleClickable {
                                        selectedIndex.value = 1
                                        navController.navigate(
                                            BottomNavigationScreens.Recommendations.name
                                        )
                                    },
                                contentAlignment = Alignment.Center
                            )
                            {
                                Icon(
                                    imageVector = Icons.Filled.ThumbUp,
                                    contentDescription = "Bottom App icon",
                                    tint = if (selectedIndex.value == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .noRippleClickable {
                                        selectedIndex.value = 2
                                        navController.navigate(
                                            BottomNavigationScreens.Map.name
                                        )
                                    },
                                contentAlignment = Alignment.Center
                            )
                            {
                                Icon(
                                    modifier = Modifier.size(36.dp),
                                    painter = painterResource(id = R.mipmap.globe),
                                    contentDescription = "Bottom App icon",
                                    tint = if (selectedIndex.value == 2) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .noRippleClickable {
                                        selectedIndex.value = 3
                                        navController.navigate(BottomNavigationScreens.Achievements.name)
                                    },
                                contentAlignment = Alignment.Center
                            )
                            {
                                Icon(
                                    modifier = Modifier.size(28.dp),
                                    painter = painterResource(id = R.mipmap.trophy),
                                    contentDescription = "Bottom App icon",
                                    tint = if (selectedIndex.value == 3) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .noRippleClickable {
                                        selectedIndex.value = 4
                                        navController.navigate(BottomNavigationScreens.Profile.name)
                                    },
                                contentAlignment = Alignment.Center
                            )
                            {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = "Bottom App icon",
                                    tint = if (selectedIndex.value == 4) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary
                                )
                            }

                        }

                    }) { innerPadding ->

                        fun showSnackBar() {
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
                        if (createEventViewModel.eventCreated.value) {
                            Log.d("MainActivity", "onCreate: event created")
                            showSnackBar()
                        }


                        NavHost(
                            navController = navController,
                            startDestination = BottomNavigationScreens.Map.name,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            val authentication: IAuthenticationClient = AuthenticationClient()
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
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BPRAppTheme {}
}