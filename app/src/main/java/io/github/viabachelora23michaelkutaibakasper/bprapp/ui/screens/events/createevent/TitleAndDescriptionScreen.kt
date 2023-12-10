package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.CreateEventScreens


@Composable
fun CreateEventTitleAndDescriptionScreen(
    navController: NavController,
    viewModel: CreateEventViewModel
) {

    var title = viewModel.title.value
    var description = viewModel.description.value
    val context = LocalContext.current

    Column {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(16.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LinearProgressIndicator(
                progress = { 0f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            Text(text = "Title and Description", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            TextField(
                value = title,
                onValueChange = {
                    title = viewModel.setTitle(it).toString()
                    viewModel.setValidTitle(it)
                },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                isError = viewModel.invalidTitle
            )

            TextField(
                value = description,
                onValueChange = {
                    description = viewModel.setDescription(it).toString()
                    viewModel.setValidDescription(it)
                },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .padding(all = 8.dp),
                isError = viewModel.invalidDescription
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Button(
                onClick = {
                    if (viewModel.invalidTitle || viewModel.invalidDescription) {
                        Toast.makeText(
                            context,
                            "Please fill in title field",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        navController.navigate(CreateEventScreens.Location.name)
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
                    Toast.makeText(
                        context,
                        "Event creation cancelled",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Text("Cancel")
            }
        }
    }
}