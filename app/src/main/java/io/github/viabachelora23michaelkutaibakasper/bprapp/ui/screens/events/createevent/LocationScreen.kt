package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.GeoLocation
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Location
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.CreateEventScreens


@Composable
fun CreateEventLocationScreen(navController: NavController, viewModel: CreateEventViewModel) {
    var location = viewModel.location.value
    val context = LocalContext.current


    val fields =
        listOf(Place.Field.ADDRESS_COMPONENTS, Place.Field.ADDRESS, Place.Field.LAT_LNG)

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
    // Start the autocomplete intent.
    val countries = mutableListOf<String>()
    countries.add("DK")
    val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
        .setCountries(countries).setTypesFilter(listOf(PlaceTypes.ADDRESS))
        .build(context)
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
                    val address = place.addressComponents?.asList()
                    val city = address?.get(2)?.name ?: ""
                    val lat = place.latLng?.latitude ?: 0.0
                    val lng = place.latLng?.longitude ?: 0.0
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

    Column {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally

        ) {
            LinearProgressIndicator(
                progress = { 0.33f * 1 },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            Text(text = "Location", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            Button(onClick = { startAutocomplete.launch(intent) }) {
                Text(text = "Select location")
            }

            Text(
                text = location.completeAddress.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Button(
                onClick = {
                    location.completeAddress?.let { viewModel.setValidAddress(it) }
                    if (viewModel.invalidAddress) {
                        Toast.makeText(
                            context,
                            "Please provide a location",
                            Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        navController.navigate(CreateEventScreens.DateAndTime.name)
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
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
                    .padding(top = 4.dp)
            ) {
                Text("Back")
            }
        }

    }
}