package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.github.viabachelora23michaelkutaibakasper.bprapp.CreateEventScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.CreateEventViewModel


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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Private")
            Switch(
                checked = isPrivate,
                onCheckedChange = { isPrivate = viewModel.setIsPrivate(it) }
            )
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
