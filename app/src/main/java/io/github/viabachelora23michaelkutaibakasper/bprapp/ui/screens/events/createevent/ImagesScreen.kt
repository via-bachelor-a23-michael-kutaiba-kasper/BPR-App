package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.Companion.isPhotoPickerAvailable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.github.viabachelora23michaelkutaibakasper.bprapp.CreateEventScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.CreateEventViewModel


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
                    isPhotoPickerAvailable(context)
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
