package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.recommendations

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@ExperimentalLayoutApi
@Composable
fun RecommendationsScreen(viewModel: RecommendationsViewModel) {
    val predefinedKeywords = viewModel.predefinedKeywords.value
    val predefinedCategories = viewModel.predefinedCategories.value
    var selectedKeywords = viewModel.selectedKeywords.value
    var selectedCategories = viewModel.selectedCategory.value
    val isLoading by viewModel.isSurveyFilled
    val user = viewModel.user
    val context = LocalContext.current


    if (viewModel.isSurveyFilled.value) {
        Text(text = "Survey filled")
    } else {
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
                                selected = selectedCategories.contains(category),
                                onClick = {
                                    selectedCategories =
                                        if (selectedCategories.contains(category)) {
                                            viewModel.setCategories(selectedCategories - category)
                                        } else {
                                            if (selectedCategories.size >= 3) {
                                                Toast.makeText(
                                                    context,
                                                    "You can only select 3 categories",
                                                    Toast.LENGTH_SHORT
                                                ).show();
                                                selectedCategories
                                            } else {
                                                viewModel.setCategories(selectedCategories + category)
                                            }
                                        }
                                },
                                leadingIcon = if (selectedCategories.contains(category)) {
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
                                selected = selectedKeywords.contains(keyword),
                                onClick = {
                                    selectedKeywords = if (selectedKeywords.contains(keyword)) {
                                        viewModel.setKeywords(selectedKeywords - keyword)
                                    } else {
                                        if (selectedKeywords.size >= 3) {
                                            Toast.makeText(
                                                context,
                                                "You can only select 3 keywords",
                                                Toast.LENGTH_SHORT
                                            ).show();
                                            selectedKeywords
                                        } else {
                                            viewModel.setKeywords(selectedKeywords + keyword)
                                        }
                                    }
                                },
                                leadingIcon = if (selectedKeywords.contains(keyword)) {
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
                    if (selectedCategories.size != 3 || selectedKeywords.size != 3) {
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
                Button(onClick = { viewModel.setSurveyFilled(hostId = user.value?.uid ?: "") }) {
                    Text(text = "Click me")
                }
            }
        }

    }
}

@ExperimentalLayoutApi
@Preview
@Composable
fun PreviewRecommendationsScreen() {
    RecommendationsScreen(RecommendationsViewModel())
}