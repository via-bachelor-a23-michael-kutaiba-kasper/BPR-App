@file:OptIn(ExperimentalMaterial3Api::class)

package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import io.github.viabachelora23michaelkutaibakasper.bprapp.BottomNavigationScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.CreateEventScreens
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@Composable
fun DisplayFormattedDateTime(dateTime: LocalDateTime, modifier: Modifier = Modifier) {
    val formatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.SHORT)
        .withLocale(Locale.UK)
    val formattedTime = dateTime.format(formatter)
    //make text bigger
    Text(text = formattedTime, fontSize = 32.sp)
}

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

    fun convertUtcMillisecondsToFormattedDate(
        utcMilliseconds: Long?
    ): LocalDate {

        return Instant
            .ofEpochMilli(utcMilliseconds!!)
            .atOffset(
                ZoneOffset.UTC
            )
            .toLocalDate()
    }

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

        val startDatePickerState =
            rememberDatePickerState(initialSelectedDateMillis = LocalDateTime.now().toMillis())
        val endDatePickerState =
            rememberDatePickerState(initialSelectedDateMillis = LocalDateTime.now().toMillis())
        val startTimeDialogState = rememberMaterialDialogState()
        val endTimeDialogState = rememberMaterialDialogState()

        var pickedStartTime by remember {
            mutableStateOf(LocalTime.now())
        }
        var pickedEndTime by remember {
            mutableStateOf(LocalTime.now().plusHours(3))
        }
        val combinedStartDateAndTime by remember {
            derivedStateOf {
                LocalDateTime.of(
                    convertUtcMillisecondsToFormattedDate(startDatePickerState.selectedDateMillis),
                    pickedStartTime
                )
            }
        }
        val combinedEndDateAndTime by remember {
            derivedStateOf {
                LocalDateTime.of(
                    convertUtcMillisecondsToFormattedDate(endDatePickerState.selectedDateMillis),
                    pickedEndTime
                )
            }
        }
        val startDateOpenDialog = remember { mutableStateOf(false) }
        val endDateOpenDialog = remember { mutableStateOf(false) }
        Text(text = "Selected start date and time:")
        DisplayFormattedDateTime(combinedStartDateAndTime, Modifier.fillMaxWidth())
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { startDateOpenDialog.value = true }) {
                Text(text = "Select start date")
            }
            Button(onClick = { startTimeDialogState.show() }) {
                Text(text = "Select start time")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Selected end date and time:")
        DisplayFormattedDateTime(combinedEndDateAndTime, Modifier.fillMaxWidth())
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { endDateOpenDialog.value = true }) {
                Text(text = "Select end date")
            }
            Button(onClick = { endTimeDialogState.show() }) {
                Text(text = "Select end time")
            }
        }

        if (startDateOpenDialog.value) {
            DatePickerDialog(
                onDismissRequest = {
                    startDateOpenDialog.value = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            startDateOpenDialog.value = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            startDateOpenDialog.value = false
                        }
                    ) {
                        Text("CANCEL")
                    }
                }
            ) {
                DatePicker(
                    state = startDatePickerState
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        MaterialDialog(
            dialogState = startTimeDialogState,
            buttons = {
                positiveButton(text = "Ok") {
                }
                negativeButton(text = "Cancel")
            }
        ) {
            timepicker(
                is24HourClock = true,
                initialTime = LocalTime.now(),
                title = "Pick a time"
            ) {
                pickedStartTime = it
            }
        }


        if (endDateOpenDialog.value) {
            DatePickerDialog(
                onDismissRequest = {
                    endDateOpenDialog.value = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            endDateOpenDialog.value = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            endDateOpenDialog.value = false
                        }
                    ) {
                        Text("CANCEL")
                    }
                }
            ) {
                DatePicker(
                    state = endDatePickerState
                )
            }
        }
        HorizontalDivider()
        MaterialDialog(
            dialogState = endTimeDialogState,
            buttons = {
                positiveButton(text = "Ok") {
                }
                negativeButton(text = "Cancel")
            }
        ) {
            timepicker(
                is24HourClock = true,
                initialTime = LocalTime.now(),
                title = "Pick a time"
            ) {
                pickedEndTime = it
            }
        }


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
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier.height(500.dp)
        ) {
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

