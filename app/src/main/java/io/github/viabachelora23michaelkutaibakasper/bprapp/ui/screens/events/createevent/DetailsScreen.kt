package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.github.viabachelora23michaelkutaibakasper.bprapp.CreateEventScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.CreateEventViewModel


@ExperimentalMaterial3Api
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateEventDetailsScreen(navController: NavController) {
    val viewModel: CreateEventViewModel = viewModel()
    var isPrivate = viewModel.isPrivate.value
    var isAdultsOnly = viewModel.isAdultsOnly.value
    var isPaid = viewModel.isPaid.value
    val predefinedKeywords = listOf("Android", "Jetpack", "Compose", "Kotlin", "UI", "Development")
    var selectedKeywords = viewModel.selectedKeywords.value
    val coffeeDrinks = arrayOf("Americano", "Cappuccino", "Espresso", "Latte", "Mocha")
    var categoryExpanded by remember { mutableStateOf(false) }
    var selectedCategory = viewModel.selectedCategory.value
    var maxNumberOfAttendees = viewModel.maxNumberOfAttendees.value


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

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Category: ", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp))
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
                        coffeeDrinks.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    viewModel.setCategory(item)
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Max number of attendees: ", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp))
            TextField(
                value = maxNumberOfAttendees.toString(),
                onValueChange = {
                    if (it.all { char -> char.isDigit() } && it.isNotEmpty() && it.toInt() <= 10000) {
                        viewModel.setMaxNumberOfAttendees(it.toInt())
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


        Text("Select Keywords: ", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
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
                            viewModel.setKeywords(selectedKeywords + keyword)
                        }
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


        val context = LocalContext.current
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

