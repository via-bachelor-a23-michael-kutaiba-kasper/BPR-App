package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.createevent

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.CreateEventScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.navigateTo
import java.io.File


@Composable
fun UploadImagePlaceHolder() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUri2 by remember { mutableStateOf<Uri?>(null) }
    var imageUri3 by remember { mutableStateOf<Uri?>(null) }
    var showImageDialog by remember { mutableStateOf(false) }
    var showImageDialog2 by remember { mutableStateOf(false) }
    var showImageDialog3 by remember { mutableStateOf(false) }

    val chooseFromGalleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
                showImageDialog = false
            }
        }
    val chooseFromGalleryLauncher2 =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri2 = it
                showImageDialog2 = false
            }
        }
    val chooseFromGalleryLauncher3 =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri3 = it
                showImageDialog3 = false
            }
        }

    val takePictureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                val file = File("path/to/image.jpg")
                imageUri = Uri.fromFile(file)

                showImageDialog = false
            }
        }
    // Dialog content
    if (showImageDialog) {
        AlertDialog(
            onDismissRequest = {
                showImageDialog = false
            },
            title = {
                Text("Choose an action")
            },
            text = {
                Column {
                    TextButton(
                        onClick = {
                            chooseFromGalleryLauncher.launch("image/*")
                        }
                    ) {
                        Row {
                            Icon(Icons.Default.Favorite, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Choose from gallery")
                        }
                    }

                    TextButton(
                        onClick = {
                            takePictureLauncher.launch(null)
                        }
                    ) {
                        Row {
                            Icon(Icons.Default.Favorite, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Take a picture")
                        }
                    }
                }
            },
            confirmButton = {
            },
            dismissButton = {
            }
        )
    }
    if (showImageDialog2) {
        AlertDialog(
            onDismissRequest = {
                showImageDialog2 = false
            },
            title = {
                Text("Choose an action")
            },
            text = {
                Column {
                    TextButton(
                        onClick = {
                            chooseFromGalleryLauncher2.launch("image/*")
                        }
                    ) {
                        Row {
                            Icon(Icons.Default.Favorite, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Choose from gallery")
                        }
                    }

                    TextButton(
                        onClick = {
                            takePictureLauncher.launch(null)
                        }
                    ) {
                        Row {
                            Icon(Icons.Default.Favorite, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Take a picture")
                        }
                    }
                }
            },
            confirmButton = {
            },
            dismissButton = {
            }
        )
    }
    if (showImageDialog3) {
        AlertDialog(
            onDismissRequest = {
                showImageDialog3 = false
            },
            title = {
                Text("Choose an action")
            },
            text = {
                Column {
                    TextButton(
                        onClick = {
                            chooseFromGalleryLauncher3.launch("image/*")
                        }
                    ) {
                        Row {
                            Icon(Icons.Default.Favorite, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Choose from gallery")
                        }
                    }

                    TextButton(
                        onClick = {
                            takePictureLauncher.launch(null)
                        }
                    ) {
                        Row {
                            Icon(Icons.Default.Favorite, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Take a picture")
                        }
                    }
                }
            },
            confirmButton = {
            },
            dismissButton = {
            }
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .fillMaxHeight(0.4f)
            .background(Color.Gray)
            .clickable {
                showImageDialog = true
            },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                modifier = Modifier
                    .padding(4.dp),
                model = ImageRequest.Builder(LocalContext.current).data(imageUri)
                    .crossfade(enable = true).build(),
                contentDescription = "Avatar Image",
                contentScale = ContentScale.Crop,
            )

            IconButton(
                onClick = {
                    imageUri = null
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
            ) {
                Icon(Icons.Default.Clear, contentDescription = "Delete")
            }
        } else {
            Icon(

                Icons.Default.Build,
                contentDescription = "Add photo",
                modifier = Modifier.fillMaxHeight(0.4f)
            )
        }
    }
    Row(Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
                .background(Color.Gray)
                .clickable {
                    showImageDialog2 = true
                },
            contentAlignment = Alignment.Center
        )
        {
            if (imageUri2 != null) {
                AsyncImage(
                    modifier = Modifier
                        .padding(4.dp),
                    model = ImageRequest.Builder(LocalContext.current).data(imageUri2)
                        .crossfade(enable = true).build(),
                    contentDescription = "Avatar Image",
                    contentScale = ContentScale.Crop,
                )
                IconButton(
                    onClick = {
                        imageUri2 = null
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                ) {
                    Icon(Icons.Default.Clear, contentDescription = "Delete")
                }
            } else {
                Icon(
                    Icons.Default.Build,
                    contentDescription = "Add photo",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.Gray)
                .clickable {
                    showImageDialog3 = true
                },
            contentAlignment = Alignment.Center
        )
        {
            if (imageUri3 != null) {
                AsyncImage(
                    modifier = Modifier
                        .padding(4.dp),
                    model = ImageRequest.Builder(LocalContext.current).data(imageUri3)
                        .crossfade(enable = true).build(),
                    contentDescription = "Avatar Image",
                    contentScale = ContentScale.Crop,
                )
                IconButton(
                    onClick = {
                        imageUri3 = null
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                ) {
                    Icon(Icons.Default.Clear, contentDescription = "Delete")
                }
            } else {
                Icon(
                    Icons.Default.Build,
                    contentDescription = "Add photo",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}


@Composable
fun CreateEventImagesScreen(navController: NavController) {
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
        UploadImagePlaceHolder()

        val context = LocalContext.current
        Button(
            onClick = {

                navigateTo(CreateEventScreens.InviteFriends.name, navController)
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
                ).show()
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
