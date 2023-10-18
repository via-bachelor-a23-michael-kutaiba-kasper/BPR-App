package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event

@Composable
fun CreateEvent(navController: NavController) {
    val viewModel: MapViewViewModel = viewModel()

    var title by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Choose Type") }
    var description by remember { mutableStateOf("") }
    var isPrivate by remember { mutableStateOf(false) }
    var isAdultsOnly by remember { mutableStateOf(false) }
    var selectedDateTime by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth().padding(all = 8.dp)
        )

        // Address
        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth().padding(all = 8.dp)
        )

        // Type (Dropdown)
        // You can use a DropdownMenu or other custom implementations
        // to create a dropdown for event types
        // Description
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth().padding(all = 8.dp)
        )



        // Checkboxes for Private and Adults-Only
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.clickable { isPrivate = !isPrivate },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isPrivate,
                    onCheckedChange = { isPrivate = it }
                )
                Text("Private Event")
            }

            Row(
                modifier = Modifier.clickable { isAdultsOnly = !isAdultsOnly },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isAdultsOnly,
                    onCheckedChange = { isAdultsOnly = it }
                )
                Text("Adults Only")
            }
        }

        val context = LocalContext.current
        // Save or submit button
        Button(
            onClick = {
              //  viewModel.createEvent();
                Toast.makeText(
                    context,
                    "Event created successfully",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Save Event")
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
