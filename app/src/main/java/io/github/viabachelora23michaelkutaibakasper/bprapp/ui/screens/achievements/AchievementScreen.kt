package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.achievements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.github.viabachelora23michaelkutaibakasper.bprapp.R
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Achievement
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.DisplayFormattedTime
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.greyScale


@Composable
fun AchievementsScreen(viewModel: AchievementsViewModel) {
    val openDialog = remember { mutableStateOf(false) }
    val isLoading by viewModel.isLoading
    val user by viewModel.user.collectAsState()
    val achievements by viewModel.achievements.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val selectedAchievement by viewModel.selectedAchievement.collectAsState()


    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            (viewModel::all)(
            )
        },
        indicator = { state, refreshTrigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = refreshTrigger,
                backgroundColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary
            )
        },
    ) {
        if (user == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Please sign in to see recommendations",
                    textAlign = TextAlign.Center
                )
                Button(onClick = { viewModel.getAchievements() }) {
                    Text(text = "Refresh")

                }
            }
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(2),

                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                content = {

                    item(span = { GridItemSpan(2) }) {
                        LevelAndExperiencePart(viewModel = viewModel)
                    }
                    item(span = { GridItemSpan(2) }) {
                        AchievementsHeader()
                    }
                    items(achievements.size) {
                        AchievementCard(
                            achievement = achievements[it],
                            onClick = { openDialogFun(openDialog, true) },
                            viewModel = viewModel
                        )
                    }
                })
        }
    }
    if (openDialog.value) {

        FocusedCardDialog(openDialog, selectedAchievement)
    }
}

@Composable
private fun FocusedCardDialog(openDialog: MutableState<Boolean>, achievement: Achievement) {
    Dialog(onDismissRequest = { onDismissRequest(openDialog, false) }) {
        val AchievementModifier = if (achievement.isAchieved == true) {
            Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(8.dp)
        } else {
            Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(8.dp)
                .greyScale()
        }
        Card(
            modifier = AchievementModifier,
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        processString(achievement.name)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    model = achievement.icon,
                    contentDescription = "achievement picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = achievement.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(8.dp),
                )
                if (achievement.isAchieved == false) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LinearProgressIndicator(
                            progress = { 0.7f },
                            strokeCap = StrokeCap.Round,
                            modifier = Modifier
                                .height(8.dp)
                                .fillMaxWidth(0.5f)
                        )
                        Text(
                            text = "${achievement.progress} / ${achievement.requirement}",
                            fontSize = 14.sp, modifier = Modifier.padding(8.dp)
                        )
                    }

                } else {
                    if (achievement.unlockDate != null) {
                        Text(text = "Unlock: ${DisplayFormattedTime(achievement.unlockDate!!)}")
                    }
                }
                Text(text = "Experience points: ${achievement.expReward}")
                Text(
                    text = "Description: ${achievement.description}",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

fun onDismissRequest(bool: MutableState<Boolean>, value: Boolean) {
    bool.value = value
}

fun openDialogFun(bool: MutableState<Boolean>, value: Boolean) {
    bool.value = value
}


@Composable
fun AchievementCard(
    achievement: Achievement,
    onClick: () -> Unit, viewModel: AchievementsViewModel
) {
    val AchievementModifier = if (achievement.isAchieved == true) {
        Modifier
            .fillMaxSize()
            .clickable {
                viewModel.setSelectedAchievement(achievement)
                onClick()
            }
    } else {
        Modifier
            .fillMaxSize()
            .clickable {
                viewModel.setSelectedAchievement(achievement)
                onClick()
            }
            .greyScale()
    }
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        modifier = AchievementModifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    processString(achievement.name)
                ),
            contentAlignment = Alignment.Center
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = achievement.icon,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .padding(16.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = achievement.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                if (achievement.isAchieved != true) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (achievement.progress != null) {
                            LinearProgressIndicator(
                                progress = {
                                    achievement.progress?.toFloat()
                                        ?.div(achievement.requirement?.toFloat() ?: 1f) ?: 0f
                                },
                                strokeCap = StrokeCap.Round,
                                modifier = Modifier
                                    .height(8.dp)
                                    .fillMaxWidth(0.5f)
                            )

                            Text(
                                text = "${achievement.progress} / ${achievement.requirement}",
                                fontSize = 14.sp, modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                } else {
                    Text(
                        text = "Unlock:${achievement.unlockDate?.let { DisplayFormattedTime(it) }}",
                        modifier = Modifier.padding(12.dp)
                    )
                }

            }
        }
    }

}

@Composable
private fun AchievementsHeader() {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Achievements",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun LevelAndExperiencePart(viewModel: AchievementsViewModel) {
    val experience by viewModel.experience.collectAsState()
    val currentProgress = experience.totalExp.toFloat() / experience.maxExp.toFloat()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(
                LocalContext.current
            )
                .data(R.mipmap.ic_launcher_round)
                .build(),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .padding(16.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = experience.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Experience",
                    modifier = Modifier.padding(12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                LinearProgressIndicator(
                    progress = { currentProgress },
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth(0.5f)
                )
                Text(text = "${experience.totalExp} / ${experience.maxExp}")
                if (currentProgress >= 0.7f) {
                    Text(
                        text = "Almost there! \uD83D\uDCAA",
                        modifier = Modifier.padding(12.dp),
                        fontSize = 16.sp
                    )
                }

            }

            Column {
                Box(contentAlignment = Alignment.Center) {
                    val color = MaterialTheme.colorScheme.primary
                    val color2 = MaterialTheme.colorScheme.background
                    Canvas(
                        modifier = Modifier
                            .size(150.dp)
                            .padding(16.dp),
                        onDraw = {
                            drawCircle(color, radius = 125f)
                        }
                    )
                    Canvas(
                        modifier = Modifier
                            .size(150.dp)
                            .padding(16.dp),
                        onDraw = {
                            drawCircle(color2, radius = 115f)
                        }
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Level", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text(
                            text = experience.level.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }


                }

            }
        }
    }
}

fun processString(input: String): Color {
    when {
        '1' in input -> {
            // Do something for case '1'
            return Color.Yellow
            // Perform action for case '1'
        }

        '2' in input -> {
            // Do something for case '2'
            return Color.Cyan
            // Perform action for case '2'
        }

        '3' in input -> {
            // Do something for case '3'
            return Color.Green
            // Perform action for case '3'
        }

        else -> {
            // Do something if none of the characters are found
            return Color.Red
        }
    }
}


@Preview
@Composable
fun AchievementsScreenPreview() {
    AchievementsScreen(AchievementsViewModel())
}