package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer
import com.github.tehras.charts.line.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.line.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import io.github.viabachelora23michaelkutaibakasper.bprapp.R
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.authentication.AuthenticationClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.authentication.IAuthenticationClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.EventRating
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.ExperienceHistory
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.notifications.NotificationClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.BottomNavigationScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.navigateTo
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.map.LoadingScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.DisplayFormattedTime
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.generateRandomColor
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.localDateTimeToUTCLocalDateTime
import java.time.LocalDateTime


@ExperimentalMaterial3Api
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val authenticationClient: IAuthenticationClient = AuthenticationClient()

    val createdEvents = 0
    val finishedJoinedEvents = 1
    val tabs = listOf("My Events", "Your Participations")
    val selectedIndex = remember { mutableIntStateOf(0) }
    val user by viewModel.user
    var sliderValue by remember { mutableFloatStateOf(0f) }
    val currentEventId = remember { mutableIntStateOf(0) }
    val isLoading by viewModel.isLoading
    val openDialog = remember { mutableStateOf(false) }
    val events by viewModel.eventList.collectAsState()
    val participatedEvents by viewModel.finishedJoinedEvents.collectAsState()
    val reviewIds by viewModel.reviewIds.collectAsState()
    val highestRatedCategory by viewModel.highestRatedCategory.collectAsState()
    val errorFetchingEvents by viewModel.errorFetchingEvents
    val launcher = authenticationClient.signIn(onAuthComplete = { result ->
        viewModel.user.value = result.user
        NotificationClient().onNewToken(NotificationClient().getToken())
    }, onAuthError = {
        viewModel.user.value = null
    })
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val token = stringResource(R.string.default_web_client_id)
    val context = LocalContext.current
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            (viewModel::allOfThem)()
        },
        indicator = { state, refreshTrigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = refreshTrigger,
                backgroundColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (user == null) {
                Text("Not logged in")
                Button(onClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token).requestEmail().build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    googleSignInClient.signOut()
                        .addOnCompleteListener { launcher.launch(googleSignInClient.signInIntent) }
                }) {
                    Text("Sign in via Google")
                }
            } else {
                if (user?.photoUrl != null) {
                    AsyncImage(
                        model = user!!.photoUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                if (user!!.displayName != null) {
                    Text(
                        text = user!!.displayName!!,
                        textAlign = TextAlign.Center,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }


                if (isLoading) {
                    LoadingScreen()
                } else if (errorFetchingEvents) {
                    RefreshButton(
                        message = "Error fetching events :("
                    )
                } else if (events.isEmpty()) {
                    RefreshButton(message = "Pull down to refresh")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier) {
                    PrimaryTabRow(selectedTabIndex = selectedIndex.intValue) {
                        tabs.forEachIndexed { index, title ->
                            Tab(text = { Text(text = title) },
                                selected = selectedIndex.intValue == index,
                                onClick = { selectedIndex.intValue = index },
                                icon = {
                                    when (index) {
                                        createdEvents -> Icon(
                                            Icons.Default.Face, contentDescription = "My Events"
                                        )

                                        finishedJoinedEvents -> Icon(
                                            Icons.Default.Menu,
                                            contentDescription = "Your Participations "
                                        )
                                    }
                                })
                        }
                    }
                    when (selectedIndex.intValue) {
                        createdEvents -> {
                            CreatedEventsTab(events, navController)
                        }

                        finishedJoinedEvents -> {
                            FinishedJoinedEventsTab(
                                participatedEvents,
                                navController,
                                reviewIds,
                                currentEventId,
                                openDialog,
                                highestRatedCategory,
                                viewModel
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (user != null) {
                Button(onClick = {
                    authenticationClient.signOut()
                    viewModel.user.value = null
                }) {
                    Text(text = "Sign out")
                }
            }


            if (openDialog.value) {
                Dialog(onDismissRequest = { openDialog.value = false }) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Please rate the event from 1-5",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Rating: $sliderValue.",
                                modifier = Modifier.padding(16.dp),
                            )

                            RatingBar(value = sliderValue,
                                style = RatingBarStyle.Fill(),
                                numOfStars = 5,
                                stepSize = StepSize.HALF,
                                onValueChange = {
                                    sliderValue = it
                                },
                                onRatingChanged = {
                                    Log.d("TAG", "onRatingChanged: $it")
                                })

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                TextButton(
                                    onClick = { openDialog.value = false },
                                    modifier = Modifier.padding(8.dp),
                                ) {
                                    Text("Dismiss")
                                }
                                TextButton(
                                    onClick = {
                                        viewModel.createReview(
                                            eventId = currentEventId.value,
                                            userId = user!!.uid,
                                            rating = sliderValue,
                                            reviewDate = localDateTimeToUTCLocalDateTime(
                                                LocalDateTime.now()
                                            ).toString()
                                        )
                                        openDialog.value = false
                                    },
                                    modifier = Modifier.padding(8.dp),
                                ) {
                                    Text("Confirm")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FinishedJoinedEventsTab(
    participatedEvents: List<MinimalEvent>,
    navController: NavController,
    reviewIds: List<EventRating>,
    currentEventId: MutableIntState,
    openDialog: MutableState<Boolean>,
    highestRatedCategory: String,
    viewModel: ProfileViewModel
) {
    val experienceHistory = viewModel.experienceHistory.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(750.dp)
            .padding(8.dp)
    ) {
        LazyColumn {
            items(participatedEvents) { event ->
                FinishedJoinedEvents(navController, event, reviewIds, currentEventId, openDialog)
            }
            if (participatedEvents.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    PiechartOfEvents(participatedEvents, "Category participations")
                }
                item {
                    Text(buildAnnotatedString {
                        append("Your favorite category, based on your ratings, is ")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(highestRatedCategory)
                        }
                        append(". Does this match with the events you have attended?\uD83E\uDD14")
                    })
                }

                item {
                    LinechartOfExpHistory(experienceHistory)

                }
            }
        }

    }
}

@Composable
private fun LinechartOfExpHistory(experienceHistory: State<List<ExperienceHistory>>) {
    Text(
        text = "Experience gained over time",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
    Row(
        Modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState())
    ) {
        Column(Modifier.fillMaxSize()) {
            LineChart(
                linesChartData = listOf(
                    LineChartData(
                        points = experienceHistory.value.map {
                            LineChartData.Point(
                                value = it.exp.toFloat(), label = DisplayFormattedTime(it.date)
                            )
                        }, lineDrawer = SolidLineDrawer(
                            color = generateRandomColor()
                        ), startAtZero = true
                    )
                ),
                modifier = Modifier
                    .height(250.dp)
                    .padding(4.dp)
                    .width(2000.dp),
                animation = simpleChartAnimation(),
                pointDrawer = FilledCircularPointDrawer(
                    color = generateRandomColor()

                ),
                xAxisDrawer = SimpleXAxisDrawer(),
                yAxisDrawer = SimpleYAxisDrawer(

                ),
                horizontalOffset = 10f,
                labels = experienceHistory.value.map { DisplayFormattedTime(it.date) },
            )
        }
    }
}


@Composable
private fun CreatedEventsTab(
    events: List<MinimalEvent>, navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(8.dp)
    ) {
        LazyColumn {
            items(events) { event ->
                CreatedEventsTab(navController, event)

            }
            if (events.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    PiechartOfEvents(events, "Created event categories")
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Attendees of your events",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        val totalAttendees = events.sumOf { it.numberOfAttendees ?: 0 }
                        val highestNumberOfAttendees = events.maxOf { it.numberOfAttendees ?: 0 }
                        val averageAttendees =
                            if (events.isNotEmpty()) totalAttendees / events.size else 0

                        Text(
                            text = "Your events have a total of $totalAttendees attendees",
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Your events have an average of $averageAttendees attendees",
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Your events have a highest of $highestNumberOfAttendees attendees",
                            fontSize = 16.sp
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun FinishedJoinedEvents(
    navController: NavController,
    event: MinimalEvent,
    reviewIds: List<EventRating>,
    currentEventId: MutableIntState,
    openDialog: MutableState<Boolean>
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navigateTo(
                "${BottomNavigationScreens.EventDetails.name}/${event.eventId}", navController
            )
        }) {
        if (event.eventId !in reviewIds.map { it.eventId }) {

            Button(modifier = Modifier.align(Alignment.End), onClick = {
                currentEventId.value = event.eventId
                openDialog.value = true
            }) {
                Text(text = "Rate the event")

            }
        } else {
            Text(text = "You have already rated this event with ${reviewIds.find { it.eventId == event.eventId }?.rating} stars")
        }
        Row(
            Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically
        ) {


            Column(
                verticalArrangement = Arrangement.Center, modifier = Modifier.padding(4.dp)
            ) {
                Row {
                    Text(text = "Title: ", fontWeight = FontWeight.Bold)
                    Text(text = event.title)
                }
                Row {
                    Text(text = "Description: ", fontWeight = FontWeight.Bold)
                    Text(text = event.description ?: "No description")
                }
                Row {
                    Text(text = "Category: ", fontWeight = FontWeight.Bold)
                    Text(text = event.selectedCategory)
                }
                Row {
                    Text(text = "Date: ", fontWeight = FontWeight.Bold)
                    Text(
                        text = DisplayFormattedTime(event.selectedStartDateTime)
                    )
                }

                Row {
                    Text(text = "Status: ", fontWeight = FontWeight.Bold)
                    Text(
                        text = if (event.selectedEndDateTime?.isBefore(
                                LocalDateTime.now()
                            ) == true
                        ) "Ended" else "Ongoing"
                    )
                }
            }
        }
    }
    HorizontalDivider()
}

@Composable
private fun PiechartOfEvents(participatedEvents: List<MinimalEvent>, message: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val categories = participatedEvents.groupingBy { it.selectedCategory }.eachCount()
            .map { (value, count) ->
                Category(name = value, count = count)
            }
        Text(text = "Statistics", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = message,
                Modifier.padding(8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            PieChart(pieChartData = PieChartData(

                slices = categories.map { category ->
                    PieChartData.Slice(
                        value = category.count.toFloat(),
                        color = category.color,
                    )
                }),
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
                animation = simpleChartAnimation(),
                sliceDrawer = SimpleSliceDrawer(sliceThickness = 100f))
        }


        categories.forEach { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Canvas(modifier = Modifier
                    .size(20.dp)
                    .padding(8.dp), onDraw = {
                    drawCircle(category.color, radius = 20f)
                })
                Text(
                    text = category.name, modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
                Text(
                    text = category.count.toString(), modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )

            }
        }
    }
}


@Composable
private fun CreatedEventsTab(
    navController: NavController, event: MinimalEvent
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navigateTo(
                "${BottomNavigationScreens.EventDetails.name}/${event.eventId}", navController
            )
        }) {
        Row(
            Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = if (event.photos?.isEmpty() != true) event.photos?.get(0) else ImageRequest.Builder(
                    LocalContext.current
                ).data(R.mipmap.no_photo).build(),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                verticalArrangement = Arrangement.Center, modifier = Modifier.padding(4.dp)
            ) {
                Row {
                    Text(text = "Title: ", fontWeight = FontWeight.Bold)
                    Text(text = event.title)
                }
                Row {
                    Text(text = "Description: ", fontWeight = FontWeight.Bold)
                    Text(text = event.description ?: "No description")
                }
                Row {
                    Text(text = "Category: ", fontWeight = FontWeight.Bold)
                    Text(text = event.selectedCategory)
                }
                Row {
                    Text(text = "Date: ", fontWeight = FontWeight.Bold)
                    Text(
                        text = DisplayFormattedTime(event.selectedStartDateTime)
                    )
                }

                Row {
                    Text(text = "Status: ", fontWeight = FontWeight.Bold)
                    Text(
                        text = if (event.selectedEndDateTime?.isBefore(
                                LocalDateTime.now()
                            ) == true
                        ) "Ended" else "Ongoing"
                    )
                }
            }
        }
    }
    HorizontalDivider()
}

data class Category(
    val name: String, val color: Color = generateRandomColor(), val count: Int = 0
)

@Composable
private fun RefreshButton(
    message: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(navController = NavController(LocalContext.current), viewModel = viewModel())
}