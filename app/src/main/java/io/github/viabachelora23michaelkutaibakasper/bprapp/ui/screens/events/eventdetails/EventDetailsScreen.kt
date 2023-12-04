package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.eventdetails

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.viabachelora23michaelkutaibakasper.bprapp.R
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.LoadingScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.DisplayFormattedTime
import kotlin.math.absoluteValue


@ExperimentalLayoutApi
@ExperimentalFoundationApi
@Composable
fun EventDetailsScreen(navController: NavController, viewModel: EventDetailsViewModel, param: Int) {
    val event = viewModel.event.value
    val isLoading by viewModel.isLoading
    Log.d("eventDetailsScreen", "event: $event")
    val user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val errorFetchingEvent by viewModel.errorFetchingEvent
    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current


    if (isLoading) {
        LoadingScreen()
    } else if (errorFetchingEvent) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Error fetching events")
            Button(onClick = { viewModel.getEvent(event.eventId) }) {
                Text(text = "Refresh")
            }
        }
    } else {
        Column() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                val pagerState = rememberPagerState(pageCount = {
                    3
                })

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 32.dp)
                ) { page ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .graphicsLayer {
                                val pageOffset = (
                                        (pagerState.currentPage - page) + pagerState
                                            .currentPageOffsetFraction
                                        ).absoluteValue
                                // We animate the alpha, between 50% and 100%
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            }
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            model = if (event.photos?.isEmpty() != true
                            ) event.photos?.get(page) else ImageRequest.Builder(LocalContext.current)
                                .data(R.mipmap.no_photo).build(),
                            contentDescription = "Avatar Image",
                            contentScale = ContentScale.Crop
                        )
                    }
                }


                Text(
                    text = viewModel.event.value.title!!,
                    Modifier
                        .padding(12.dp)
                        .align(Alignment.Start),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                {
                    Text(
                        text = "Time: ${
                            DisplayFormattedTime(
                                dateTime = viewModel.event.value.selectedStartDateTime!!
                            )
                        } - ${
                            DisplayFormattedTime(
                                dateTime = viewModel.event.value.selectedEndDateTime!!
                            )
                        }",
                        Modifier
                            .padding(8.dp)
                            .align(Alignment.Start),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text(
                    text = viewModel.event.value.location?.completeAddress!!,
                    Modifier
                        .padding(8.dp)
                        .align(Alignment.Start), fontSize = 16.sp
                )
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    )
                    {
                        Text(
                            text = "Category: ${viewModel.event.value.selectedCategory}",
                            Modifier.padding(8.dp),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    if (user?.photoUrl != null) {
                        AsyncImage(
                            model = viewModel.event.value.host?.photoUrl,
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    if (user!!.displayName != null) {
                        Column(
                            modifier = Modifier.padding(
                                start = 4.dp,
                                top = 0.dp,
                                end = 0.dp,
                                bottom = 0.dp
                            )
                        ) {
                            viewModel.event.value.host?.displayName?.let {
                                Text(
                                    text = it,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Text(
                                text = "Host",
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "What is this event about:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = viewModel.event.value.description!!,
                        Modifier.padding(24.dp)
                    )
                }

                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (viewModel.event.value.maxNumberOfAttendees == 0) "Unlimited spots"
                            else "Spots left: ${viewModel.event.value.maxNumberOfAttendees}",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Free event: ",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold
                        )
                        val isPaid = viewModel.event.value.isPaid
                        Text(
                            text = if (isPaid == true) "Yes" else "No",
                            textAlign = TextAlign.Center,
                        )

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Adults only: ",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold
                        )
                        val adultsOnly = viewModel.event.value.isAdultsOnly
                        Text(
                            text = if (adultsOnly == true) "Yes" else "No",
                            textAlign = TextAlign.Center,
                        )

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Private: ",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold
                        )
                        val isPrivate = viewModel.event.value.isPrivate
                        Text(
                            text = if (isPrivate == true) "Yes" else "No",
                            textAlign = TextAlign.Center,
                        )

                    }

                }


                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    for (keyword in viewModel.event.value.selectedKeywords!!) {
                        Card(
                            modifier = Modifier.padding(4.dp),
                            colors = CardColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                                Color.Unspecified,
                                Color.Unspecified
                            )
                        ) {
                            Text(
                                text = keyword!!,
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.SemiBold
                            )

                        }
                    }
                }
                if (!event.url.isNullOrEmpty()) {
                    val intent =
                        remember { Intent(Intent.ACTION_VIEW, Uri.parse(event.url)) }
                    Button(onClick = { context.startActivity(intent) })
                    {
                        Text(text = "Link to event")
                    }
                }
                Text(
                    text = "Last updated: ${DisplayFormattedTime(dateTime = viewModel.event.value.lastUpdatedDate!!)}",
                    Modifier.padding(24.dp), fontSize = 12.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.LightGray)
            ) {
                Row {
                    if (4 == 5) {
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Text(text = "Edit event")
                        }
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Text(text = "Delete event")
                        }
                    }
                }
                Row {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text(text = "Share event")
                    }
                    Button(
                        onClick = { openDialog.value = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text(text = "Join event")
                    }
                }
            }
        }
        if (openDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                text = {
                    Text("Are you sure you want to join this event?")
                },
                confirmButton = {
                    Button(

                        onClick = {
                            openDialog.value = false
                            viewModel.joinEvent(
                                eventId = viewModel.event.value.eventId,
                                userId = user!!.uid
                            )
                        }) {
                        Text("Yes, join!")
                    }
                },
                dismissButton = {
                    Button(

                        onClick = {
                            openDialog.value = false
                        }) {
                        Text("No")
                    }
                }
            )
        }
    }
}