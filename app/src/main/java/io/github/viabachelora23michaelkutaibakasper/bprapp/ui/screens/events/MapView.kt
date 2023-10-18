package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event

class MapView {


    @Composable
    fun Map() {
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
    }

    @Composable
    fun CountrySpecific(code: String) {
        var response by remember { mutableStateOf<List<Event>>(mutableListOf()) }
        val viewModel = MapViewViewModel()
        //   val countryClient: CountryClient = ApolloCountryClient()

        val events = viewModel.eventList.collectAsState(emptyList()).value
        if (events != null) {
            response = events



            Column {

                Text(text = "Country: ${response[0].name}")
                Text(text = "Country code: ${response[0].code ?: "No code"}")
                Text(text = "Capital: ${response[0].capital ?: "No capital"}")
                HorizontalDivider()
            }
        }
    }
}