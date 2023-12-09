package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.achievements

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.greyScale
import io.github.viabachelora23michaelkutaibakasper.bprapp.util.localDateTimeToUTCLocalDateTime
import java.time.LocalDateTime


@Composable
fun AchievementsScreen(viewModel: AchievementsViewModel) {


    val openDialog = remember { mutableStateOf(false) }
    val isLoading by viewModel.isLoading
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val selectedAchievement = remember {
        mutableStateOf(
            Achievement(
                title = "Music Maestro",
                description = "Host 5 music events",
                points = 10,
                id = 1,
                isAchieved = false
            )
        )
    }
    val achievements = listOf(
        Achievement(
            title = "Music Maestro",
            description = "Host 5 music events",
            points = 10,
            id = 1,
            isAchieved = true
        ),
        Achievement(
            title = "Athlete",
            description = "Host 5 sport related events",
            points = 20,
            id = 1,
            isAchieved = false
        ), Achievement(
            title = "Music Maestro",
            description = "Host 5 music events",
            points = 10,
            id = 1,
            isAchieved = false
        ),
        Achievement(
            title = "Athlete",
            description = "Host 5 sport related events",
            points = 20,
            id = 1,
            isAchieved = true
        )
    )
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            (viewModel::getAchievements)(
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
        LazyVerticalGrid(columns = GridCells.Fixed(2),

            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            content = {

                item(span = { GridItemSpan(2) }) {
                    LevelAndExperiencePart()
                }
                item(span = { GridItemSpan(2) }) {
                    AchievementsHeader()
                }
                //sort by isAchieved and list isAchieved last
                val sortedAchievements = achievements.sortedByDescending { !it.isAchieved }
                items(sortedAchievements.size) {
                    AchievementCard(
                        achievement = sortedAchievements[it],
                        onClick = { openDialogFun(openDialog, true) },
                        selectedAchievement = selectedAchievement
                    )
                }
            })
    }
    if (openDialog.value) {

        FocusedCardDialog(openDialog, selectedAchievement.value)
    }
}

@Composable
private fun FocusedCardDialog(openDialog: MutableState<Boolean>, achievement: Achievement) {
    Dialog(onDismissRequest = { onDismissRequest(openDialog, false) }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        val AchievementModifier = if (achievement.isAchieved) {
            Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp)
        } else {
            Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp)
                .greyScale()
        }
        Card(
            modifier = AchievementModifier,
            border = BorderStroke(1.dp, Color.Black),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Cyan),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
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
                Text(
                    text = achievement.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp),
                )
                if (!achievement.isAchieved) {
                    Column(
                        modifier = Modifier.padding(16.dp),
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
                            text = "3 / 5",
                            fontSize = 14.sp, modifier = Modifier.padding(12.dp)
                        )
                    }

                } else {
                    Text(text = "Unlocked: 20/12/2023")
                }
                Text(text = "Points: ${achievement.points}")
                Text(text = "Description: ${achievement.description}")
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

fun SetSelectedAchievement(
    achievement: Achievement,
    selectedAchievement: MutableState<Achievement>
) {
    selectedAchievement.value = achievement
}


@Composable
fun AchievementCard(
    achievement: Achievement,
    selectedAchievement: MutableState<Achievement>,
    onClick: () -> Unit
) {
    val AchievementModifier = if (achievement.isAchieved) {
        Modifier
            .fillMaxSize()
            .clickable {
                SetSelectedAchievement(achievement, selectedAchievement)
                onClick()
            }
    } else {
        Modifier
            .fillMaxSize()
            .clickable {
                SetSelectedAchievement(achievement, selectedAchievement)
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
                .background(Color.Cyan),
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
                Text(
                    text = achievement.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                if (!achievement.isAchieved) {
                    Column(
                        modifier = Modifier.padding(16.dp),
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
                            text = "3 / 5",
                            fontSize = 14.sp, modifier = Modifier.padding(12.dp)
                        )
                    }
                } else {
                    Text(text = "Unlocked: 20/12/2023",modifier = Modifier.padding(12.dp))
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
private fun LevelAndExperiencePart() {
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
                    progress = { 0.7f },
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth(0.5f)
                )
                Text(
                    text = "Almost there! \uD83D\uDCAA",
                    modifier = Modifier.padding(12.dp),
                    fontSize = 16.sp
                )
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
                        Text(text = "1", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }


                }

            }
        }
    }
}


@Preview
@Composable
fun AchievementsScreenPreview() {
    AchievementsScreen(AchievementsViewModel())
}