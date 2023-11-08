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
import androidx.compose.material3.LinearProgressIndicator
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
import com.google.android.material.progressindicator.LinearProgressIndicator
import io.github.viabachelora23michaelkutaibakasper.bprapp.BottomNavigationScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.CreateEventScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event


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

@Composable
fun CreateEventDateAndTimeScreen(navController: NavController) {
    val viewModel: CreateEventViewModel = viewModel()
    var title = viewModel.title.value
    var description = viewModel.description.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = { 0.2f * 2 },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Text(text = "Date and Time")
        val context = LocalContext.current
        // Save or submit button
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
fun CreateEventDetailsScreen(navController: NavController) {
    val viewModel: CreateEventViewModel = viewModel()
    var title = viewModel.title.value
    var description = viewModel.description.value
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
    var title = viewModel.title.value
    var description = viewModel.description.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = { 0.2f * 4 },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Text(text = "Images")
        val context = LocalContext.current
        // Save or submit button
        Button(
            onClick = {
                navController.navigate(CreateEventScreens.InviteFriends.name)
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
