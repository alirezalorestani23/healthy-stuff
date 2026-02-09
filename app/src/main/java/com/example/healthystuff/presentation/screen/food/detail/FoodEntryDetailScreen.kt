package com.example.healthystuff.presentation.screen.food.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthystuff.R
import com.example.healthystuff.presentation.component.Kicker
import com.example.healthystuff.presentation.component.MacroChipsRow
import com.example.healthystuff.presentation.component.SportyBackground
import com.example.healthystuff.presentation.component.SportyHeader


@Composable
fun FoodEntryDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: FoodEntryDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    SportyBackground(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SportyHeader(
                title = "Details",
                kicker = "Meal breakdown",
                iconRes = R.drawable.ic_food
            )

            if (uiState.isLoading) {
                CircularProgressIndicator()
                return@Column
            }
            uiState.error?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
                return@Column
            }

            val entry = uiState.entry
            if (entry == null) {
                Text(text = "Entry not found.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                return@Column
            }

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Kicker(text = entry.title)
                    Text(text = "${entry.totalCalories} kcal", style = MaterialTheme.typography.titleLarge)
                    MacroChipsRow(
                        proteinG = entry.totalProteinG,
                        carbsG = entry.totalCarbsG,
                        fatG = entry.totalFatG
                    )
                }
            }
            Kicker(text = "Items")

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(entry.items) { item ->
                    ElevatedCard(shape = RoundedCornerShape(22.dp)) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = "${item.calories} kcal",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "P ${item.proteinG}g  C ${item.carbsG}g  F ${item.fatG}g",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}
