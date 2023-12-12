@file:OptIn(
    MapsComposeExperimentalApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)

package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.map

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import io.github.viabachelora23michaelkutaibakasper.bprapp.MainActivity
import io.github.viabachelora23michaelkutaibakasper.bprapp.R
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.MinimalEvent
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.BottomNavigationScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.CreateEventScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.navigateTo
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.theme.AppTheme
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.DisplayFormattedTime
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.localDateTimeToUTCLocalDateTime
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Map(navController: NavController, modifier: Modifier = Modifier, viewModel: MapViewViewModel) {
    val mapView = 0
    val listView = 1
    val tabs = listOf("Map", "List")
    val selectedIndex = remember { mutableIntStateOf(0) }

    Column(modifier = Modifier)
    {
        PrimaryTabRow(selectedTabIndex = selectedIndex.intValue) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(text = title) },
                    selected = selectedIndex.intValue == index,
                    onClick = { selectedIndex.intValue = index },
                    icon = {
                        when (index) {
                            mapView -> Icon(Icons.Default.Place, contentDescription = "Map")
                            listView -> Icon(
                                Icons.Default.Menu,
                                contentDescription = "List "
                            )
                        }
                    }
                )
            }
        }

        when (selectedIndex.intValue) {
            mapView -> {
                MapEvents(
                    navController = navController,
                    initialZoomPosition = LatLng(55.862207, 9.844651),
                    Modifier.fillMaxSize(), viewModel = viewModel
                )
            }

            listView -> {
                EventList(viewModel, navController)
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun EventList(viewModel: MapViewViewModel, navController: NavController) {
    var response by remember { mutableStateOf<List<MinimalEvent>>(emptyList()) }
    val events by viewModel.eventList.collectAsState(emptyList())
    val isLoading by viewModel.isLoading
    val errorFetchingEvents by viewModel.errorFetchingEvent
    response = events
    Log.d("eventlist", "events: $response")
    val groupedEvents = response.groupBy { it.selectedCategory }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    if (isLoading) {
        LoadingScreen()
    } else if (errorFetchingEvents) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Error fetching events")
            Button(onClick = {
                viewModel.getEvents(
                    from = localDateTimeToUTCLocalDateTime(
                        LocalDateTime.now()
                    ).toString()
                )
            }) {
                Text(text = "Refresh")
            }
        }
    } else if (response.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "no events :(")
            Button(onClick = {
                viewModel.getEvents(
                    from = localDateTimeToUTCLocalDateTime(
                        LocalDateTime.now().minusHours(1)
                    ).toString()
                )
            }) {
                Text(text = "Refresh")
            }
        }
    } else {

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                (viewModel::getEvents)(
                    localDateTimeToUTCLocalDateTime(
                        LocalDateTime.now()
                    ).toString()
                )
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
            LazyColumn {
                groupedEvents.forEach { (category, events) ->
                    stickyHeader {
                        Text(
                            text = if (category != "Un Assigned") category else "No category",
                            fontWeight = Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.primary)
                                .padding(4.dp), textAlign = TextAlign.Center
                        )
                    }
                    items(events) { event ->
                        EventListItem(event, navController)
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@ExperimentalMaterial3Api
@Composable
fun MapEvents(
    navController: NavController,
    initialZoomPosition: LatLng,
    modifier: Modifier = Modifier,
    viewModel: MapViewViewModel
) {
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current
    val user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialZoomPosition, 10f)
    }
    val fetchedEvents by viewModel.clusterEvents.collectAsState(emptyList())
    Log.d("events for makers", "getEvents: $fetchedEvents")

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = true,
                zoomControlsEnabled = false,
                compassEnabled = true,
                mapToolbarEnabled = true,
                rotationGesturesEnabled = true,
                tiltGesturesEnabled = true
            )
        )
    }

    Box(modifier = Modifier) {
        val useMyposition by MainActivity().usemylocation.collectAsState()

        GoogleMap(
            modifier = Modifier,
            cameraPositionState = cameraPositionState,
            googleMapOptionsFactory = { GoogleMapOptions().mapId(context.getString(R.string.map_id)) },
            uiSettings = uiSettings,

            properties =

            MapProperties(
                isMyLocationEnabled = useMyposition
            )
        ) {
            Clustering(
                items = fetchedEvents,
                onClusterClick = {
                    // Handle cluster click
                    viewModel.clusterClicked.value = true
                    viewModel.currentClusterItems.value =
                        it.items.map { item -> item as MapViewViewModel.EventClusterItem }
                    true
                },
                onClusterItemInfoWindowClick = {
                    // Handle cluster item info window click
                    navigateTo(
                        "${BottomNavigationScreens.EventDetails.name}/${it.eventId}",
                        navController
                    )
                    true
                },

                )
        }

        if (viewModel.clusterClicked.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    viewModel.clusterClicked.value = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                LazyColumn(content = {
                    items(viewModel.currentClusterItems.value) { event ->
                        ClusterModalItem(navController, event, viewModel)
                    }
                })
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        FloatingActionButton(
            onClick = {
                if (user != null) {
                    navigateTo(CreateEventScreens.Title.name, navController)
                } else {
                    Toast.makeText(
                        context,
                        "You need to be logged in to create an event",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }, content = {
                Column {
                    Icon(Icons.Default.Add, contentDescription = "Create event")
                }
            }, modifier = Modifier
                .padding(16.dp)
                .size(56.dp)
                .align(Alignment.BottomEnd)
        )
    }

}

@Composable
private fun ClusterModalItem(
    navController: NavController,
    event: MapViewViewModel.EventClusterItem,
    viewModel: MapViewViewModel
) {
    Column(modifier = Modifier.clickable {
        navigateTo("${BottomNavigationScreens.EventDetails.name}/${event.eventId}", navController)
        viewModel.clusterClicked.value = false
    }) {
        Row(Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = if (event.photos?.isEmpty() != true
                ) {
                    event.photos?.get(0)
                } else if (event.host == stringResource(id = R.string.Faengslet)) {
                    ImageRequest.Builder(
                        LocalContext.current
                    )
                        .data(R.mipmap.faengletlogo)
                        .build()
                } else {
                    ImageRequest.Builder(LocalContext.current)
                        .data(R.mipmap.no_photo).build()
                },
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(4.dp)
            ) {
                Row {
                    Text(text = "Title: ", fontWeight = Bold)
                    Text(text = event.title1)
                }
                Row {
                    Text(text = "Start date: ", fontWeight = Bold)
                    Text(
                        text = DisplayFormattedTime(event.selectedStartDateTime)
                    )
                }
            }
        }
    }
}


@Composable
fun LoadingScreen() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}


@Composable
fun EventListItem(event: MinimalEvent, navController: NavController) {
    Column(modifier = Modifier.clickable {
        navigateTo("${BottomNavigationScreens.EventDetails.name}/${event.eventId}", navController)
    }) {

        Row(Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = if (event.photos?.isEmpty() != true
                ) {
                    event.photos?.get(0)
                } else if (event.host.displayName == stringResource(id = R.string.Faengslet)) {
                    ImageRequest.Builder(
                        LocalContext.current
                    )
                        .data(R.mipmap.faengletlogo)
                        .build()
                } else {
                    ImageRequest.Builder(LocalContext.current)
                        .data(R.mipmap.no_photo).build()
                },
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(4.dp)) {
                Row {
                    Text(text = "Title: ", fontWeight = Bold)
                    Text(text = event.title)
                }
                Row {
                    Text(text = "Location: ", fontWeight = Bold)
                    Text(text = event.location.completeAddress ?: "No location")
                }
                Row {
                    Text(text = "Category: ", fontWeight = Bold)
                    Text(text = if (event.selectedCategory != "Un Assigned") " ${event.selectedCategory}" else "No category")
                }
                Row {
                    Text(text = "Start date: ", fontWeight = Bold)
                    Text(
                        text = DisplayFormattedTime(event.selectedStartDateTime)
                    )
                }
            }
        }
        HorizontalDivider()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        EventListItem(
            event = MinimalEvent(
                title = "Title",
                selectedStartDateTime = LocalDateTime.now(),
                eventId = 1,
                selectedCategory = "Category",
                photos = listOf("photo"),
                location = Location(
                    city = "City",
                    completeAddress = "Address",
                    geoLocation = GeoLocation(
                        lat = 0.0,
                        lng = 0.0
                    )
                ), description = "Description",
                selectedEndDateTime = LocalDateTime.now(),
                host = User(
                    displayName = "Michael Kuta Ibaka",
                    userId = "123456789",
                    photoUrl = null,
                    creationDate = LocalDateTime.now(),
                    lastSeenOnline = LocalDateTime.now()
                ), numberOfAttendees = null
            ), navController = NavController(LocalContext.current)
        )
    }
}


