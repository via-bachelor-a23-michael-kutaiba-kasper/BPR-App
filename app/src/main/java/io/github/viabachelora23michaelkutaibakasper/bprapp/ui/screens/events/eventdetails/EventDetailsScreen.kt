package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.eventdetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.viabachelora23michaelkutaibakasper.bprapp.R
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.map.LoadingScreen
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.DisplayFormattedTime
import kotlin.math.absoluteValue


@ExperimentalLayoutApi
@ExperimentalFoundationApi
@Composable
fun EventDetailsScreen(navController: NavController, viewModel: EventDetailsViewModel, param: Int) {
    val event by viewModel.event.collectAsState()
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
            Text(text = "Error fetching event :(")
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


                eventPhotos(event)


                FirstSectionOfEventDetails(viewModel, user)

                BooleanFields(viewModel)


                KeywordsSection(viewModel)
                AttendeesSection(viewModel)

                LinkToEvent(event = event, context = context, viewModel = viewModel)
            }

            EditandDeleteButtons(user, viewModel)

            ShareandJoinButtons(user, viewModel, openDialog)
        }
        ConfirmationDialog(openDialog, viewModel, user, context)
    }
}

@Composable
private fun ConfirmationDialog(
    openDialog: MutableState<Boolean>,
    viewModel: EventDetailsViewModel,
    user: FirebaseUser?,
    context: Context
) {
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
                        if (!viewModel.invalidJoin.value) {
                            openDialog.value = false
                            viewModel.joinEvent(
                                eventId = viewModel.event.value.eventId,
                                userId = user!!.uid
                            )
                        } else {
                            Toast.makeText(context, "Event is full", Toast.LENGTH_SHORT).show()
                        }
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

@Composable
private fun LinkToEvent(
    event: Event,
    context: Context,
    viewModel: EventDetailsViewModel
) {
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

@Composable
private fun ShareandJoinButtons(
    user: FirebaseUser?,
    viewModel: EventDetailsViewModel,
    openDialog: MutableState<Boolean>
) {
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
        if (user?.uid != viewModel.event.value.host?.userId) {
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

@Composable
private fun EditandDeleteButtons(
    user: FirebaseUser?,
    viewModel: EventDetailsViewModel
) {
    if (user?.uid == viewModel.event.value.host?.userId) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Row {
                if (user?.uid == viewModel.event.value.host?.userId) {
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
        }
    }
}

@Composable
private fun AttendeesSection(viewModel: EventDetailsViewModel) {
    Text(
        text = "Attendees",
        Modifier
            .padding(12.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )
    if (viewModel.event.value.attendees!!.isEmpty()) {
        Text(
            text = "No attendees yet :(",
            Modifier
                .padding(12.dp),
            fontSize = 12.sp,
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(8.dp)
        ) {
            LazyColumn(content = {
                items(viewModel.event.value.attendees!!.size) { index ->
                    val attendee = viewModel.event.value.attendees!![index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        if (attendee?.photoUrl != null) {
                            AsyncImage(
                                model = attendee.photoUrl,
                                contentDescription = "Profile picture",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        if (attendee!!.displayName != null) {
                            Column(
                                modifier = Modifier.padding(
                                    start = 4.dp,
                                    top = 0.dp,
                                    end = 0.dp,
                                    bottom = 0.dp
                                )
                            ) {
                                attendee.displayName?.let {
                                    Text(
                                        text = it,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Text(
                                    text = "Attendee",
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            })
        }
    }
}

@ExperimentalLayoutApi
@Composable
private fun KeywordsSection(viewModel: EventDetailsViewModel) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        if (viewModel.event.value.selectedKeywords?.size == 1) {
            Text(
                text = "No keywords",
                Modifier
                    .padding(12.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        } else {
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
    }
}

@Composable
private fun BooleanFields(viewModel: EventDetailsViewModel) {
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
                else {
                    "Spots left: ${
                        viewModel.event.value.attendees?.let {
                            viewModel.event.value.maxNumberOfAttendees?.minus(
                                it.size
                            )
                        }
                    }/${viewModel.event.value.maxNumberOfAttendees}"
                },
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
            val adultsOnly = viewModel.event.value.adultsOnly
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
}

@Composable
private fun FirstSectionOfEventDetails(
    viewModel: EventDetailsViewModel,
    user: FirebaseUser?
) {
    Text(
        text = viewModel.event.value.title!!,
        Modifier
            .padding(12.dp),
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
                .padding(8.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
    Text(
        text = viewModel.event.value.location?.completeAddress!!,
        Modifier
            .padding(8.dp), fontSize = 16.sp
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
                text = if (viewModel.event.value.selectedCategory != "Un Assigned") "Category: ${viewModel.event.value.selectedCategory}" else "No category",
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
}

@ExperimentalFoundationApi
@Composable
private fun eventPhotos(
    event: Event
) {
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
                .height(150.dp)
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            Log.d("eventDetailsScreen", "displayname: ${event.host?.displayName}")
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                model = if (event.photos?.isEmpty() != true
                ) {
                    event.photos?.get(0)
                } else if (event.host?.displayName == stringResource(id = R.string.Faengslet)) {
                    ImageRequest.Builder(
                        LocalContext.current
                    )
                        .data(R.mipmap.faengletlogo)
                        .build()
                } else {
                    ImageRequest.Builder(LocalContext.current)
                        .data(R.mipmap.no_photo).build()
                },
                contentDescription = "Avatar Image",
                contentScale = ContentScale.Fit
            )
        }
    }
}