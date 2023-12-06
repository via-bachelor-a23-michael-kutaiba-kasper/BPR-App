package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import io.github.viabachelora23michaelkutaibakasper.bprapp.R
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in.AuthenticationClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in.IAuthenticationClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.BottomNavigationScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.LoadingScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.DisplayFormattedTime
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.localDateTimeToUTCLocalDateTime
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.roundToNearestHalf
import java.time.LocalDateTime
import kotlin.math.roundToInt


@ExperimentalMaterial3Api
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val authenticationClient: IAuthenticationClient = AuthenticationClient()
    val user by viewModel.user
    var sliderValue by remember { mutableFloatStateOf(0f) }
    val currentEventId = remember { mutableIntStateOf(0) }
    val isLoading by viewModel.isLoading
    val openDialog = remember { mutableStateOf(false) }
    val events by viewModel.eventList.collectAsState()
    val participatedEvents by viewModel.finishedJoinedEvents.collectAsState()
    val reviewIds by viewModel.reviewIds.collectAsState()
    val errorFetchingEvents by viewModel.errorFetchingEvents
    val launcher = authenticationClient.signIn(onAuthComplete = { result ->
        viewModel.user.value = result.user
    }, onAuthError = {
        viewModel.user.value = null
    })
    val token = stringResource(R.string.default_web_client_id)
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user == null) {
            Text("Not logged in")
            Button(onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token).requestEmail().build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                googleSignInClient.signOut()
                    .addOnCompleteListener { launcher.launch(googleSignInClient.signInIntent) }
            }) {
                Text("Sign in via Google")
            }
        } else {
            if (user?.photoUrl != null) {
                AsyncImage(
                    model = user!!.photoUrl,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            if (user!!.displayName != null) {
                Text(
                    text = user!!.displayName!!,
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))
            }


            if (isLoading) {
                LoadingScreen()
            } else if (errorFetchingEvents) {
                RefreshButton(
                    message = "Error fetching events :("
                )
            } else if (events.isEmpty()) {
                RefreshButton(message = "No events created :(")
            }
            Button(onClick = {
               viewModel.allOfThem()

            }) {
                Text(text = "Refresh")
            }
            Text(
                text = "Your events",
                Modifier
                    .padding(12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(8.dp)
            ) {
                LazyColumn {
                    items(events) { event ->
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("${BottomNavigationScreens.EventDetails.name}/${event.eventId}") }) {
                            Row(
                                Modifier.padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = if (event.photos?.isEmpty() != true) event.photos?.get(0) else ImageRequest.Builder(
                                        LocalContext.current
                                    ).data(R.mipmap.no_photo).build(),
                                    contentDescription = "Profile picture",
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )

                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Row {
                                        Text(text = "Title: ", fontWeight = FontWeight.Bold)
                                        Text(text = event.title ?: "No title")
                                    }
                                    Row {
                                        Text(text = "Description: ", fontWeight = FontWeight.Bold)
                                        Text(text = event.description ?: "No description")
                                    }
                                    Row {
                                        Text(text = "Category: ", fontWeight = FontWeight.Bold)
                                        Text(text = event.selectedCategory ?: "No category")
                                    }
                                    Row {
                                        Text(text = "Date: ", fontWeight = FontWeight.Bold)
                                        Text(
                                            text = DisplayFormattedTime(event.selectedStartDateTime)
                                        )
                                    }

                                    Row {
                                        Text(text = "Status: ", fontWeight = FontWeight.Bold)
                                        Text(
                                            text = if (event.selectedEndDateTime?.isBefore(
                                                    LocalDateTime.now()
                                                ) == true
                                            ) "Ended" else "Ongoing"
                                        )
                                    }
                                }
                            }
                        }
                        HorizontalDivider()

                    }
                }
            }

            Text(
                text = "Your participated events",
                Modifier
                    .padding(12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(8.dp)
            ) {
                LazyColumn {
                    items(participatedEvents) { event ->
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("${BottomNavigationScreens.EventDetails.name}/${event.eventId}") }) {
                            Row(
                                Modifier.padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (event.eventId !in reviewIds) {

                                    Button(onClick = {
                                        currentEventId.value = event.eventId
                                        openDialog.value = true
                                    }) {
                                        Text(text = "Rate the event")

                                    }
                                }

                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Row {
                                        Text(text = "Title: ", fontWeight = FontWeight.Bold)
                                        Text(text = event.title ?: "No title")
                                    }
                                    Row {
                                        Text(text = "Description: ", fontWeight = FontWeight.Bold)
                                        Text(text = event.description ?: "No description")
                                    }
                                    Row {
                                        Text(text = "Category: ", fontWeight = FontWeight.Bold)
                                        Text(text = event.selectedCategory ?: "No category")
                                    }
                                    Row {
                                        Text(text = "Date: ", fontWeight = FontWeight.Bold)
                                        Text(
                                            text = DisplayFormattedTime(event.selectedStartDateTime)
                                        )
                                    }

                                    Row {
                                        Text(text = "Status: ", fontWeight = FontWeight.Bold)
                                        Text(
                                            text = if (event.selectedEndDateTime?.isBefore(
                                                    LocalDateTime.now()
                                                ) == true
                                            ) "Ended" else "Ongoing"
                                        )
                                    }
                                }
                            }
                        }
                        HorizontalDivider()

                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            authenticationClient.signOut()
            viewModel.user.value = null
        }) {
            Text(text = "Sign out")
        }

        if (openDialog.value) {
            Dialog(onDismissRequest = { openDialog.value = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = "Please rate the event from 1-5", fontWeight = FontWeight.Bold)
                        Text(
                            text = "Rating: $sliderValue.",
                            modifier = Modifier.padding(16.dp),
                        )

                        Slider(
                            modifier = Modifier.padding(4.dp),
                            value = sliderValue,
                            onValueChange = { sliderValue = it.roundToNearestHalf() },
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.primary,
                                activeTrackColor = MaterialTheme.colorScheme.primary,
                                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                            ),
                            steps = 10,
                            valueRange = 0f..5f,
                            thumb = {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    tint = Color.Red,
                                    contentDescription = null
                                )
                            }
                        )



                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            TextButton(
                                onClick = { openDialog.value = false },
                                modifier = Modifier.padding(8.dp),
                            ) {
                                Text("Dismiss")
                            }
                            TextButton(
                                onClick = {
                                    viewModel.createReview(
                                        eventId = currentEventId.value,
                                        userId = user!!.uid,
                                        rating = sliderValue,
                                        reviewDate = localDateTimeToUTCLocalDateTime(LocalDateTime.now()).toString()
                                    )
                                    openDialog.value = false
                                },
                                modifier = Modifier.padding(8.dp),
                            ) {
                                Text("Confirm")
                            }
                        }
                    }
                }
            }

        }

    }
}


@Composable
private fun RefreshButton(
    message: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(navController = NavController(LocalContext.current), viewModel = viewModel())
}