@file:OptIn(ExperimentalMaterial3Api::class)

package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import android.net.Uri
import android.util.Log
import android.widget.ScrollView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.material.progressindicator.LinearProgressIndicator
import io.github.viabachelora23michaelkutaibakasper.bprapp.BottomNavigationScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.CreateEventScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.R
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import java.net.URI
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.TimeZone


@Composable
fun CreateEventTitleAndDescriptionScreen(navController: NavController) {
    val viewModel: CreateEventViewModel = viewModel()
    var title = viewModel.title.value
    var description = viewModel.description.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = { 0f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        // Title
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        )

        // Type (Dropdown)
        // You can use a DropdownMenu or other custom implementations
        // to create a dropdown for event types
        // Description
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        )

        val context = LocalContext.current
        // Save or submit button
        Button(
            onClick = {
                navController.navigate(CreateEventScreens.Location.name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Next")
        }
        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "Event creation cancelled",
                    Toast.LENGTH_SHORT
                ).show();
                // pop back to the previous screen (the Map)
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Cancel")
        }
    }

}

@Composable
fun CreateEventLocationScreen(navController: NavController) {
    val viewModel: CreateEventViewModel = viewModel()
    var address = viewModel.address.value
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

fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

@Composable
fun CreateEventDateAndTimeScreen(navController: NavController) {
    val viewModel: CreateEventViewModel = viewModel()

    //make this column scrollable so that the user can scroll down to see the date and time picker
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LinearProgressIndicator(
            progress = { 0.2f * 2 },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Text(text = "Date and Time")
        val dateTime = LocalDateTime.now()
        val dateRangePickerState = rememberDateRangePickerState(
            initialSelectedStartDateMillis = dateTime.toMillis(),
            initialDisplayedMonthMillis = null,
            initialSelectedEndDateMillis = dateTime.plusDays(3).toMillis(),
            initialDisplayMode = DisplayMode.Input,
            yearRange = IntRange(dateTime.year, dateTime.year + 1)
        )
        DateRangePicker(state = dateRangePickerState,Modifier.height(400.dp))
        HorizontalDivider()

        val timePickerState = remember {
            TimePickerState(
                is24Hour = true,
                initialHour = dateTime.hour,
                initialMinute = dateTime.minute

            )
        }

        TimePicker(
            state = timePickerState,

            modifier = Modifier
                .padding(top = 8.dp)
        )

        //create a localdatetimer to store the date and time from the date and time picker
        fun convertUtcMillisecondsToFormattedDate(
            utcMilliseconds: Long,
            dateFormat: String
        ): String {
            val date = Date(utcMilliseconds)
            val sdf = SimpleDateFormat(dateFormat)
            sdf.timeZone = TimeZone.getTimeZone("UTC") // Set the desired time zone
            return sdf.format(date)
        }

        val date = convertUtcMillisecondsToFormattedDate(
            dateRangePickerState.selectedStartDateMillis!!,
            "yyyy-MM-dd"
        )
        Log.d("date", date)
        val time = timePickerState.hour.toString() + ":" + timePickerState.minute.toString()
        Log.d("time", time)

        // Save or submit button
        val context = LocalContext.current
        Button(
            onClick = {
                navController.navigate(CreateEventScreens.Details.name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Next")
        }
        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "Event creation cancelled",
                    Toast.LENGTH_SHORT
                ).show();
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


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateEventDetailsScreen(navController: NavController) {
    val viewModel: CreateEventViewModel = viewModel()
    var isPrivate = viewModel.isPrivate.value
    var isAdultsOnly = viewModel.isAdultsOnly.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        LinearProgressIndicator(
            progress = { 0.2f * 3 },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Text(text = "Details")
        //create a switch for private and adults only
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Private")
            //create switch that toggles between private and public
            Switch(
                checked = isPrivate,
                onCheckedChange = { isPrivate = it }
            )
        }



        val context = LocalContext.current
        // Save or submit button
        Button(
            onClick = {
                navController.navigate(CreateEventScreens.Images.name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Next")
        }
        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "Event creation cancelled",
                    Toast.LENGTH_SHORT
                ).show();
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


@Composable
fun CreateEventImagesScreen(navController: NavController) {
    val viewModel: CreateEventViewModel = viewModel()
    val context = LocalContext.current
    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 6)
    ) {
        if (it != null) {
            Log.d("PhotoPicker", "Selected URI: $it")
            selectedImageUris = it
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        LinearProgressIndicator(
            progress = { 0.2f * 4 },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp), modifier = Modifier.height(500.dp)) {
            items(selectedImageUris) { uri ->
                AsyncImage(
                    modifier = Modifier
                        .size(250.dp)
                        .padding(4.dp),
                    model = ImageRequest.Builder(LocalContext.current).data(uri)
                        .crossfade(enable = true).build(),
                    contentDescription = "Avatar Image",
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Button(onClick = {
                Toast.makeText(
                    context,
                    ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable()
                        .toString(),
                    Toast.LENGTH_LONG
                ).show()
            }) {
                Text(text = "Availability")
            }

            Spacer(modifier = Modifier.width(24.dp))
            Button(onClick = {
                multiplePhotoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Text(text = "Pick multiple photo")
            }
        }
        val context = LocalContext.current
        Button(
            onClick = {
                navController.navigate(CreateEventScreens.Details.name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Next")
        }
        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "Event creation cancelled",
                    Toast.LENGTH_SHORT
                ).show();
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

@Composable
fun CreateEventInviteFriendsScreen(navController: NavController) {
    val viewModel: CreateEventViewModel = viewModel()
    var title = viewModel.title.value
    var description = viewModel.description.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = { 0.2f * 5 },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Text(text = "Invite Friends")
        val context = LocalContext.current
        // Save or submit button
        Button(
            onClick = {
                navController.navigate(BottomNavigationScreens.Map.name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Create Event")
        }
        Button(
            onClick = {
                Toast.makeText(
                    context,
                    "Event creation cancelled",
                    Toast.LENGTH_SHORT
                ).show();
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

