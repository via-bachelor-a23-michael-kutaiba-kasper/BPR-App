package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.utils.noRippleClickable
import io.github.viabachelora23michaelkutaibakasper.bprapp.R

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val selectedIndex = remember { mutableIntStateOf(2) }
    AnimatedNavigationBar(
        selectedIndex = selectedIndex.value,
        modifier = Modifier
            .height(68.dp),

        ballAnimation = Straight(
            tween(400)
        ),
        indentAnimation = Height(tween(500)),
        ballColor = MaterialTheme.colorScheme.primary,
        barColor = MaterialTheme.colorScheme.primary
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable {
                    selectedIndex.value = 0

                },
            contentAlignment = Alignment.Center
        )
        {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Bottom App icon",
                tint = if (selectedIndex.value == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable {
                    selectedIndex.value = 1
                    navController.navigate(
                        BottomNavigationScreens.Recommendations.name
                    )
                },
            contentAlignment = Alignment.Center
        )
        {
            Icon(
                imageVector = Icons.Filled.ThumbUp,
                contentDescription = "Bottom App icon",
                tint = if (selectedIndex.value == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable {
                    selectedIndex.value = 2
                    navController.navigate(
                        BottomNavigationScreens.Map.name
                    )
                },
            contentAlignment = Alignment.Center
        )
        {
            Icon(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = R.mipmap.globe),
                contentDescription = "Bottom App icon",
                tint = if (selectedIndex.value == 2) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable {
                    selectedIndex.value = 3
                    navController.navigate(BottomNavigationScreens.Achievements.name)
                },
            contentAlignment = Alignment.Center
        )
        {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.mipmap.trophy),
                contentDescription = "Bottom App icon",
                tint = if (selectedIndex.value == 3) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable {
                    selectedIndex.value = 4
                    navController.navigate(BottomNavigationScreens.Profile.name)
                },
            contentAlignment = Alignment.Center
        )
        {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Bottom App icon",
                tint = if (selectedIndex.value == 4) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary
            )
        }

    }
}