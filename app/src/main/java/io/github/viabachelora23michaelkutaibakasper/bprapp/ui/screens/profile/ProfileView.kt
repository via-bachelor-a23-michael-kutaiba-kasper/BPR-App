package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.profile

import android.util.Log
import android.widget.RatingBar
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.viabachelora23michaelkutaibakasper.bprapp.R
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in.AuthenticationClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.sign_in.IAuthenticationClient
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation.BottomNavigationScreens
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.EventListItem
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.LoadingScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.MapViewViewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.DisplayFormattedTime
import java.time.LocalDateTime


@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val authenticationClient: IAuthenticationClient = AuthenticationClient()
    val user by viewModel.user
    val isLoading by viewModel.isLoading
    val events by viewModel.eventList.collectAsState()
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
            Text(
                text = "Your events",
                Modifier
                    .padding(12.dp)
                    .align(Alignment.Start),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            if (isLoading) {
                LoadingScreen()
            } else if (errorFetchingEvents) {
                RefreshButton(
                    message = "Error fetching events :("
                )
            } else if (events.isEmpty()) {
                RefreshButton(message = "No events created :(")
            }
            Button(onClick = { viewModel.getEvents(hostId = user!!.uid, includePrivate = true) }) {
                Text(text = "Refresh")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(8.dp)
            ) {
                LazyColumn {
                    items(events) { event ->
                        Column(modifier = Modifier.clickable { navController.navigate("${BottomNavigationScreens.EventDetails.name}/${event.eventId}") }) {
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
        }
        Button(onClick = {
            authenticationClient.signOut()
            viewModel.user.value = null
        }) {
            Text(text = "Sign out")
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

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(navController = NavController(LocalContext.current), viewModel = viewModel())
}