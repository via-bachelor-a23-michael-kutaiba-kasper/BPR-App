package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

import io.github.viabachelora23michaelkutaibakasper.bprapp.data.validators.isEndDateBeforeStartDate
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.CreateEventScreens
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
fun CreateEventDateAndTimeScreen(navController: NavController, viewModel: CreateEventViewModel) {
    val selectedStartDateTime = viewModel.selectedStartDateTime.value
    val selectedEndDateTime = viewModel.selectedEndDateTime.value

    val startDatePickerState =
        rememberDatePickerState(initialSelectedDateMillis = selectedStartDateTime.toMillis())
    val endDatePickerState =
        rememberDatePickerState(initialSelectedDateMillis = selectedEndDateTime.toMillis())
    val startTimeDialogState = rememberMaterialDialogState()
    val endTimeDialogState = rememberMaterialDialogState()
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
    val context = LocalContext.current

    Column {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LinearProgressIndicator(
                progress = { 0.33f * 2 },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            Text(text = "Date and time", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)

            Text(
                text = "Selected start date and time: ${
                    displayFormattedTime(
                        combinedStartDateAndTime
                    )
                }", Modifier.fillMaxWidth(), fontSize = 16.sp, fontWeight = FontWeight.SemiBold
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { startDateOpenDialog.value = true }) {
                    Text(text = "Select start date")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Selected end date and time: ${
                    displayFormattedTime(
                        combinedEndDateAndTime
                    )
                }",
                Modifier.fillMaxWidth(),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { endDateOpenDialog.value = true }) {
                    Text(text = "Select end date")
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
                                startTimeDialogState.show()
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
                                endTimeDialogState.show()
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
        }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Button(
                    onClick = {
                        if (isEndDateBeforeStartDate(
                                combinedStartDateAndTime,
                                combinedEndDateAndTime
                            )
                        ) {
                            Toast.makeText(
                                context,
                                "End date cannot be before start date",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            viewModel.setSelectedDateTime(combinedStartDateAndTime)
                            viewModel.setSelectedEndDateTime(combinedEndDateAndTime)
                            navController.navigate(CreateEventScreens.Details.name)
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

fun displayFormattedTime(
    dateTime: LocalDateTime
): String {
    val formatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.SHORT)
        .withLocale(Locale.UK)
    return dateTime.format(formatter)
}
