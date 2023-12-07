package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.recommendations

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseUser
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.EventListItem
import io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events.LoadingScreen


@ExperimentalLayoutApi
@Composable
fun RecommendationsScreen(viewModel: RecommendationsViewModel, navController: NavController) {
    val predefinedKeywords = viewModel.predefinedKeywords.value
    val predefinedCategories = viewModel.predefinedCategories.value
    var selectedKeywords = viewModel.selectedKeywords.value
    var selectedCategories = viewModel.selectedCategory.value
    val isLoading by viewModel.isLoading
    val user = viewModel.user
    val context = LocalContext.current
    val response by viewModel.recommendationsList.collectAsState(emptyList())
    val errorFetchingEvents by viewModel.errorFetchingEvents

    if (viewModel.isSurveyFilled.value) {
        if (isLoading) {
            LoadingScreen()
        } else if (errorFetchingEvents) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Error fetching events")
                RefreshRecommendationsButton(viewModel, user)
            }
        } else if (response.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "no events :(")
                RefreshRecommendationsButton(viewModel, user)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Recommendations",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "These are the events we think you will like the most. Have fun! :)",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                RefreshRecommendationsButton(viewModel, user)
                LazyColumn {
                    items(response) { event ->
                        EventListItem(event, navController)
                    }
                }
            }

        }

    } else {
        InterestSurvey(
            predefinedCategories,
            selectedCategories,
            viewModel,
            context,
            predefinedKeywords,
            selectedKeywords,
            user
        )

    }
}

@ExperimentalLayoutApi
@Composable
private fun InterestSurvey(
    predefinedCategories: List<String>,
    selectedCategories: List<String>,
    viewModel: RecommendationsViewModel,
    context: Context,
    predefinedKeywords: List<String>,
    selectedKeywords: List<String>,
    user: MutableState<FirebaseUser?>
) {
    var selectedCategories1 = selectedCategories
    var selectedKeywords1 = selectedKeywords
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = "Interest Survey",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Please complete this survey to get the best recommendations for you!",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Please select 3 of these Categories that interest you the most",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold
        )
        Card(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                FlowColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(bottom = 8.dp)
                ) {
                    for (category in predefinedCategories) {
                        FilterChip(
                            modifier = Modifier.padding(4.dp),
                            label = { Text(category) },
                            selected = selectedCategories1.contains(category),
                            onClick = {
                                selectedCategories1 =
                                    if (selectedCategories1.contains(category)) {
                                        viewModel.setCategories(selectedCategories1 - category)
                                    } else {
                                        if (selectedCategories1.size >= 3) {
                                            Toast.makeText(
                                                context,
                                                "You can only select 3 categories",
                                                Toast.LENGTH_SHORT
                                            ).show();
                                            selectedCategories1
                                        } else {
                                            viewModel.setCategories(selectedCategories1 + category)
                                        }
                                    }
                            },
                            leadingIcon = if (selectedCategories1.contains(category)) {
                                {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = "liked icon",
                                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                                    )
                                }
                            } else {
                                null
                            }
                        )
                    }
                }
            }
        }
        Text(
            text = "And select 3 of these keywords that interest you the most",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold
        )
        Card(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                FlowColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(bottom = 8.dp)
                ) {
                    for (keyword in predefinedKeywords) {
                        FilterChip(
                            modifier = Modifier.padding(4.dp),
                            label = { Text(keyword) },
                            selected = selectedKeywords1.contains(keyword),
                            onClick = {
                                selectedKeywords1 = if (selectedKeywords1.contains(keyword)) {
                                    viewModel.setKeywords(selectedKeywords1 - keyword)
                                } else {
                                    if (selectedKeywords1.size >= 3) {
                                        Toast.makeText(
                                            context,
                                            "You can only select 3 keywords",
                                            Toast.LENGTH_SHORT
                                        ).show();
                                        selectedKeywords1
                                    } else {
                                        viewModel.setKeywords(selectedKeywords1 + keyword)
                                    }
                                }
                            },
                            leadingIcon = if (selectedKeywords1.contains(keyword)) {
                                {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = "liked icon",
                                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                                    )
                                }
                            } else {
                                null
                            }
                        )
                    }
                }
            }
        }
        Button(
            onClick = {
                if (selectedCategories1.size != 3 || selectedKeywords1.size != 3) {
                    Toast.makeText(
                        context,
                        "Please select 3 categories and 3 keywords",
                        Toast.LENGTH_SHORT
                    ).show();
                } else {
                    /*TODO*/
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Submit Survey")
        }
        Text(text = "Recommendations")
        if (viewModel.isSurveyFilled.value) {
            Text(text = "Survey filled")
        } else {
            Text(text = "Survey not filled")
            Button(onClick = {
                viewModel.setSurveyFilled(
                    hostId = user.value?.uid ?: ""
                )
            }) {
                Text(text = "Click me")
            }
        }
    }
}

@Composable
private fun RefreshRecommendationsButton(
    viewModel: RecommendationsViewModel,
    user: MutableState<FirebaseUser?>
) {
    Button(onClick = {
        viewModel.getRecommendations(
            userId = user.value?.uid ?: "",
            numberOfEvents = 5
        )
    }) {
        Text(text = "Refresh")
    }
}
