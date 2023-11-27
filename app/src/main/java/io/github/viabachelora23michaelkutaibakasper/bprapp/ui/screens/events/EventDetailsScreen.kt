package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box


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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.viabachelora23michaelkutaibakasper.bprapp.R
import kotlin.math.absoluteValue


@ExperimentalLayoutApi
@ExperimentalFoundationApi
@Composable
fun EventDetailsScreen(navController: NavController) {
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val openDialog = remember { mutableStateOf(false) }
    //create list of my 3 cat images in values folder
    val catList = listOf(R.drawable.cat1, R.drawable.cat2, R.drawable.cat3)
    Column {


        Column(
            modifier = Modifier
                .background(Color.LightGray)
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
                            // Calculate the absolute offset for the current page from the
                            // scroll position. We use the absolute value which allows us to mirror
                            // any effects for both directions
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
                        model = ImageRequest.Builder(LocalContext.current).data(catList[page])
                            .crossfade(enable = true).build(),
                        contentDescription = "Avatar Image",
                        contentScale = ContentScale.Crop,
                    )
                }
            }

            Text(
                text = "Event Titel",
                Modifier
                    .padding(12.dp)
                    .align(Alignment.Start), fontSize = 24.sp, fontWeight = FontWeight.SemiBold
            )
            Card(
                Modifier
                    .fillMaxWidth()

            )
            {
                Text(
                    text = "Time: 27/11/2023 12:00 - 27/11/2023 12:30",
                    Modifier
                        .padding(8.dp)
                        .align(Alignment.Start), fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = "Banegårdsplads 2, 8700 Horsens",
                Modifier
                    .padding(8.dp)
                    .align(Alignment.Start), fontSize = 16.sp
            )
            Card(
                Modifier
                    .fillMaxWidth()

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    Text(text = "Experience gained: 20", Modifier.padding(8.dp), fontWeight = FontWeight.SemiBold)
                    VerticalDivider(thickness = 1.dp, color = Color.Black)
                    Text(text = "Category: Music", Modifier.padding(8.dp), fontWeight = FontWeight.SemiBold)

                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                //  if (user?.photoUrl != null)

                AsyncImage(
                    model = catList[0],
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                //   if (user!!.displayName != null)
                //   {
                Column(
                    modifier = Modifier.padding(
                        start = 4.dp,
                        top = 0.dp,
                        end = 0.dp,
                        bottom = 0.dp
                    )
                ) {
                    Text(
                        text = "Mic M. Micsen",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Host",
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Card(
                Modifier
                    .fillMaxWidth()

            ) {
                Text(
                    text = "This works perfectly. top column has weight of 1F and bottom column has no weight. Is there a reason why the bottom column is now actually on the bottom?",
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
                        text = "328 is attending (5 of your friends)",
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
                    Text(
                        text = "No",
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
                    Text(
                        text = "Yes",
                        textAlign = TextAlign.Center,
                    )

                }
            }

            val predefinedKeywords =
                listOf("Android", "Jetpack", "Compose", "Kotlin", "UI", "Development")
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                for (keyword in predefinedKeywords) {
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
                            text = keyword,
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.SemiBold
                        )

                    }
                }
            }
            Text(text = "Last updated: 20/20/1998 12:31", Modifier.padding(24.dp))

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


@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Preview
@Composable
fun EventDetailsScreenPreview() {
    EventDetailsScreen(navController = rememberNavController())
}