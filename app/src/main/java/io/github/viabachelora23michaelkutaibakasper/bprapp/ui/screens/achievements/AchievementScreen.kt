package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.achievements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.github.viabachelora23michaelkutaibakasper.bprapp.R
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Achievement


@Composable
fun AchievementCard(achievement: Achievement) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        modifier = Modifier
            .fillMaxSize()
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
                    .fillMaxWidth()
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
                LinearProgressIndicator(
                    progress = { 0.7f },
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth(0.5f)
                )
                Text(
                    text = "3 / 5",
                    fontSize = 14.sp, modifier = Modifier.padding(12.dp)
                )
            }
        }
    }

}

@Composable
fun AchievementsScreen(viewModel: AchievementsViewModel) {


    val achievements = listOf(
        Achievement(
            title = "Music Maestro",
            description = "Host 5 music events",
            points = 10,
            id = 1
        ),
        Achievement(
            title = "Athlete",
            description = "Host 5 sport related events",
            points = 20,
            id = 1
        ), Achievement(
            title = "Music Maestro",
            description = "Host 5 music events",
            points = 10,
            id = 1
        ),
        Achievement(
            title = "Athlete",
            description = "Host 5 sport related events",
            points = 20,
            id = 1
        )
    )
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        content = {

            item(span = { GridItemSpan(2) }) {
                LevelAndExperiencePart()
            }
            item (span = { GridItemSpan(2) }) {
                Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Achievements",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start
                    )
                }
            }
            items(achievements.size) {
                AchievementCard(achievement = achievements[it])
            }
        })
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