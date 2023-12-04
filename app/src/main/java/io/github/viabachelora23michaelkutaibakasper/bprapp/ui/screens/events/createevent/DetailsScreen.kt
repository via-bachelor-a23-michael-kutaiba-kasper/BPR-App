package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.User
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.CreateEventScreens
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


@ExperimentalMaterial3Api
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateEventDetailsScreen(navController: NavController, viewModel: CreateEventViewModel) {
    val user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val context = LocalContext.current
    var isPrivate = viewModel.isPrivate.value
    var isAdultsOnly = viewModel.isAdultsOnly.value
    var isPaid = viewModel.isPaid.value
    val predefinedKeywords = viewModel.predefinedKeywords.value
    val predefinedCategories = viewModel.predefinedCategories.value
    var selectedKeywords = viewModel.selectedKeywords.value

    var categoryExpanded by remember { mutableStateOf(false) }
    val selectedCategory = viewModel.selectedCategory.value
    val maxNumberOfAttendees = viewModel.maxNumberOfAttendees.value


    Column {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            LinearProgressIndicator(
                progress = { 0.33f * 3 },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            Text(text = "Details", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Category: ",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = categoryExpanded,
                        onExpandedChange = {
                            categoryExpanded = !categoryExpanded
                        }
                    ) {
                        TextField(
                            value = selectedCategory,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = { categoryExpanded = false }
                        ) {
                            predefinedCategories.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        viewModel.setCategory(item)
                                        viewModel.setValidCategory(item)
                                        categoryExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Max number of attendees: ",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
                TextField(
                    value = maxNumberOfAttendees.toString(),
                    onValueChange = {
                        if (it.all { char -> char.isDigit() } && it.isNotEmpty() && it.toInt() <= 10000) {
                            viewModel.setMaxNumberOfAttendees(it.toInt())
                            if (viewModel.maxNumberOfAttendees.value == 0) {
                                viewModel.setMaxNumberOfAttendees(-1)
                            }
                        }
                    },
                    label = { Text("Enter Number") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Private", fontWeight = FontWeight.Bold)
                Switch(
                    checked = isPrivate,
                    onCheckedChange = { isPrivate = viewModel.setIsPrivate(it) }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Paid event", fontWeight = FontWeight.Bold)
                Switch(
                    checked = isPaid,
                    onCheckedChange = { isPaid = viewModel.setIsPaid(it) }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Adult event", fontWeight = FontWeight.Bold)
                Switch(
                    checked = isAdultsOnly,
                    onCheckedChange = { isAdultsOnly = viewModel.setIsAdultsOnly(it) }
                )
            }
            Text(
                "Select 3-5 Keywords ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                FlowColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(bottom = 8.dp)
                ) {
                    for (keyword in predefinedKeywords) {
                        FilterChip(
                            modifier = Modifier.padding(4.dp),
                            label = { Text(keyword) },
                            selected = selectedKeywords.contains(keyword),
                            onClick = {
                                selectedKeywords = if (selectedKeywords.contains(keyword)) {
                                    viewModel.setKeywords(selectedKeywords - keyword)
                                } else {
                                    if (selectedKeywords.size >= 5) {
                                        Toast.makeText(
                                            context,
                                            "You can only select up to 5 keywords",
                                            Toast.LENGTH_SHORT
                                        ).show();
                                        selectedKeywords
                                    } else {
                                        viewModel.setKeywords(selectedKeywords + keyword)
                                    }
                                }
                                viewModel.setValidKeywords(selectedKeywords)
                            },
                            leadingIcon = if (selectedKeywords.contains(keyword)) {
                                {
                                    Icon(
                                        imageVector = Icons.Filled.Done,
                                        contentDescription = "Done icon",
                                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                                    )
                                }
                            } else {
                                null
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(52.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Button(
                onClick = {
                    viewModel.setHost(
                        User(
                            user?.displayName!!, user?.uid!!, user?.photoUrl,
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(user?.metadata?.creationTimestamp!!),
                                ZoneId.systemDefault()
                            ),
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(user?.metadata?.lastSignInTimestamp!!),
                                ZoneId.systemDefault()
                            )

                        )
                    )

                    if (viewModel.invalidCategory || viewModel.invalidKeywords) {
                        Toast.makeText(
                            context,
                            "Please select a category and 3-5 keywords",
                            Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        viewModel.setEvent()
                        navController.navigate(CreateEventScreens.EventSummary.name)
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

