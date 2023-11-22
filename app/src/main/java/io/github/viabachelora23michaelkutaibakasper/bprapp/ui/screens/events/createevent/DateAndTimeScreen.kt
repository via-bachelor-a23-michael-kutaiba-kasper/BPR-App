package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import io.github.viabachelora23michaelkutaibakasper.bprapp.CreateEventScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.CreateEventViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale


fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

@ExperimentalMaterial3Api
@Composable
fun CreateEventDateAndTimeScreen(navController: NavController) {
    val viewModel: CreateEventViewModel = viewModel()
    var selectedStartDateTime = viewModel.selectedStartDateTime.value
    var selectedEndDateTime = viewModel.selectedEndDateTime.value

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
            rememberDatePickerState(initialSelectedDateMillis = selectedStartDateTime.toMillis())
        val endDatePickerState =
            rememberDatePickerState(initialSelectedDateMillis = selectedEndDateTime.toMillis())
        val startTimeDialogState = rememberMaterialDialogState()
        val endTimeDialogState = rememberMaterialDialogState()

        var pickedStartTime by remember {
            mutableStateOf(selectedStartDateTime.toLocalTime())
        }
        var pickedEndTime by remember {
            mutableStateOf(selectedEndDateTime.toLocalTime())
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
                viewModel.setSelectedDateTime(
                    combinedStartDateAndTime
                )
                Log.d(
                    "CreateEventDateAndTimeScreen",
                    "Selected start date and time: $combinedStartDateAndTime"
                )
                viewModel.setSelectedEndDateTime(
                    combinedEndDateAndTime
                )
                Log.d(
                    "CreateEventDateAndTimeScreen",
                    "Selected end date and time: $combinedEndDateAndTime"
                )
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
fun DisplayFormattedDateTime(dateTime: LocalDateTime, modifier: Modifier = Modifier) {
    val formatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.SHORT)
        .withLocale(Locale.UK)
    val formattedTime = dateTime.format(formatter)
    //make text bigger
    Text(text = formattedTime, fontSize = 32.sp)
}
