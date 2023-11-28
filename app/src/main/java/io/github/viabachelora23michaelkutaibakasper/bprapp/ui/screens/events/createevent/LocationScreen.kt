package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import io.github.viabachelora23michaelkutaibakasper.bprapp.CreateEventScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.CreateEventViewModel


@Composable
fun CreateEventLocationScreen(navController: NavController) {
    val viewModel: CreateEventViewModel = viewModel()
    var location = viewModel.location.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = { 0.2f * 1 },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Text(text = "Location")
        val context = LocalContext.current


        val fields = listOf(Place.Field.ADDRESS_COMPONENTS, Place.Field.ADDRESS, Place.Field.LAT_LNG)

        fun getMetaDataValue(context: Context, key: String): String? {
            try {
                val ai: ApplicationInfo =
                    context.packageManager.getApplicationInfo(
                        context.packageName,
                        PackageManager.GET_META_DATA
                    )
                val bundle: Bundle = ai.metaData
                return bundle.getString(key)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return null
        }

        Places.initializeWithNewPlacesApiEnabled(
            context, getMetaDataValue(context, "com.google.android.geo.API_KEY")!!
        )

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(context)

        // Start the autocomplete intent.
        val countries = mutableListOf<String>()
        countries.add("DK")
        var intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .setCountries(countries).setTypesFilter(listOf(PlaceTypes.ADDRESS))
            .build(context)


        var latLng by remember {
            mutableStateOf("LatLng")
        }
        var address1 by remember {
            mutableStateOf("address")
        }
        val startAutocomplete =
            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent1 = result.data
                    if (intent1 != null) {
                        val place = Autocomplete.getPlaceFromIntent(intent1)
                        Log.i(
                            ContentValues.TAG, "Place: ${place.name}, ${place.id}"
                        )
                        //  address = place.addressComponents?.toString() ?: "Address"
                        // create location object based on address components
                        val address = place.addressComponents?.asList()
                        val city = address?.get(2)?.name ?: ""
                        val lat = place.latLng?.latitude ?: 0.0
                        val lng = place.latLng?.longitude ?: 0.0
                        latLng = "Lat: $lat, Lng: $lng"
                        address1 = place.address ?: "Address"
                        location = Location(
                            city,
                            address1,
                            GeoLocation(lat, lng)
                        )
                        Log.i(ContentValues.TAG, "Location: $location")
                        viewModel.setLocation(location)
                    }
                } else if (result.resultCode == Activity.RESULT_CANCELED) {
                    // The user canceled the operation.
                    Log.i(ContentValues.TAG, "User canceled autocomplete")
                }
            }


        Button(onClick = { startAutocomplete.launch(intent) }) {
            Text(text = "Select location")
        }

        Text(text = location.toString())

        // Save or submit button
        Button(
            onClick = {
                navController.navigate(CreateEventScreens.DateAndTime.name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Next")
        }
        Button(
            onClick = {
                // pop back to the previous screen (the Map)
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Back")
        }
    }

}