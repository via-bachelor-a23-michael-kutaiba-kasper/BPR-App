package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.theme.BPRAppTheme

class MapView {

    @Composable
    fun Map(navController: NavController) {

        Column(modifier = Modifier.fillMaxSize())
        {
            val mapView = 0
            val listView = 1
            val tabs = listOf("Map", "List")
            val selectedIndex = remember { mutableIntStateOf(0) }
            PrimaryTabRow(selectedTabIndex = selectedIndex.intValue) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(text = title) },
                        selected = selectedIndex.intValue == index,
                        onClick = { selectedIndex.intValue = index }
                    )
                }

            }

            when (selectedIndex.intValue) {
                mapView -> {
                    mapEvents()
                }
                listView -> {
                    EventList()
                }
            }
        }
    }


    @Composable
    fun EventList() {
        var response by remember { mutableStateOf<List<Event>>(emptyList()) }
        val viewModel: MapViewViewModel = viewModel()

        val events by viewModel.eventList.collectAsState(emptyList())
        response = events
        Log.d("ApolloEventClient", "getEvents: $response")


        LazyColumn {
            items(response) { country ->
                Column {

                    HorizontalDivider()
                }

            }
        }
    }

    @Composable
    fun mapEvents() {
        val horsens = LatLng(55.862207, 9.844651)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(horsens, 15f)
        }
        val uiSettings by remember {
            mutableStateOf(
                MapUiSettings(
                    myLocationButtonEnabled = true,
                    zoomControlsEnabled = false,
                    compassEnabled = true,
                    mapToolbarEnabled = true,
                    rotationGesturesEnabled = true, tiltGesturesEnabled = true
                )
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = uiSettings,
                properties = MapProperties(isMyLocationEnabled = true)
            ) {
                Marker(
                    state = MarkerState(position = LatLng(55.862207, 9.844651)),
                    title = "run guys",
                    snippet = "description"
                )
            }

            FloatingActionButton(
                onClick = {}, content = {
                    Column {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }, modifier = Modifier
                    .padding(16.dp)
                    .size(56.dp)
                    .align(Alignment.BottomEnd)
            )
        }

    }



    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        BPRAppTheme {
            // Map()
        }
    }
}

