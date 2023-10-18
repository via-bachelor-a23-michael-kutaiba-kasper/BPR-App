package io.github.viabachelora23michaelkutaibakasper.bprapp.ui.screens.events

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.viabachelora23michaelkutaibakasper.bprapp.data.domain.Event

class MapListView {

    @Composable
    fun EventList() {
        var response by remember { mutableStateOf<List<Event>>(emptyList()) }
        val viewModel:MapViewViewModel = viewModel()

        // val countryClient: CountryClient = ApolloCountryClient()

        val events by viewModel.eventList.collectAsState(emptyList())
        response = events
        Log.d("ApolloCountryClient", "getCountries: $response")


        LazyColumn {
            items(response) { country ->
                Column {
                    Text("Country: ${country.name ?: "No name"}")
                    Text(text = "Country code: ${country.code ?: "No code"}")
                    Text(text = "Capital: ${country.capital ?: "No capital"}")
                    HorizontalDivider()
                }

            }
        }
    }
}