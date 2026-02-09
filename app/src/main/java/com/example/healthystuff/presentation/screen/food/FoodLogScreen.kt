package com.example.healthystuff.presentation.screen.food

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthystuff.R
import com.example.healthystuff.domain.model.FoodItem
import com.example.healthystuff.presentation.component.EntryCard
import com.example.healthystuff.presentation.component.Kicker
import com.example.healthystuff.presentation.component.SportyBackground
import com.example.healthystuff.presentation.component.SportyHeader
import com.example.healthystuff.presentation.component.SummaryCard
import com.example.healthystuff.presentation.screen.food.components.AddFoodEntryDialog
import com.example.healthystuff.presentation.screen.food.components.AiMealEstimateDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun FoodLogScreen(
    onEntryClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FoodLogViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val aiState by viewModel.aiState.collectAsState()


    val selectedDate by viewModel.selectedDate.collectAsState()

    val dateText = DateTimeFormatter.ofPattern("EEE, MMM d").format(selectedDate)

    var showAddDialog by remember { mutableStateOf(false) }
    var showAiDialog by remember { mutableStateOf(false) }
    var showAiReviewDialog by remember { mutableStateOf(false) }
    var aiReviewTitle by remember { mutableStateOf("") }
    var aiReviewItems by remember { mutableStateOf<List<FoodItem>>(emptyList()) }


    LaunchedEffect(aiState.estimate) {
        val estimate = aiState.estimate ?: return@LaunchedEffect
        aiReviewTitle = estimate.title
        aiReviewItems = estimate.items.map {
            FoodItem(
                id = 0,
                name = it.name,
                quantity = null,
                unit = null,
                calories = it.calories,
                proteinG = it.proteinG.roundToInt(),
                carbsG = it.carbsG.roundToInt(),
                fatG = it.fatG.roundToInt(),
                confidence = it.confidence
            )
        }
        showAiDialog = false
        showAiReviewDialog = true

    }
    SportyBackground(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SportyHeader(
                title = "Food",
                kicker = dateText,
                iconRes = R.drawable.ic_food
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FilledTonalButton(onClick = { viewModel.setSelectedDate(selectedDate.minusDays(1)) }) {
                    Text(text = "Prev")
                }

                Text(
                    text = dateText,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                FilledTonalButton(onClick = {
                    val next = selectedDate.plusDays(1)
                    if (next <= LocalDate.now()) viewModel.setSelectedDate(next)
                }) {
                    Text(text = "Next")
                }
            }

            SummaryCard(
                title = if (selectedDate == LocalDate.now()) "Today" else dateText,
                calories = uiState.daySummary.totalCalories,
                targetCalories = 2000,
                proteinG = uiState.daySummary.totalProteinG,
                carbsG = uiState.daySummary.totalCarbsG,
                fatG = uiState.daySummary.totalFatG
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { showAddDialog = true },
                    modifier = Modifier.weight(1f)
                ) { Text("Add meal") }

                FilledTonalButton(
                    onClick = { showAiDialog = true },
                    modifier = Modifier.weight(1f)
                ) { Text("AI estimate") }
            }

            Kicker(text = "Entries")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(uiState.entries) { entry ->
                    EntryCard(
                        title = entry.title,
                        calories = entry.totalCalories,
                        timestampMillis = entry.timestampMillis,
                        modifier = Modifier.clickable { onEntryClick(entry.id) }
                    )
                }
            }
        }
    }
    if (showAddDialog) {
        AddFoodEntryDialog(
            onDismiss = { showAddDialog = false },
            onSave = { title, items ->
                viewModel.addManualEntry(title, items)
                showAddDialog = false
            })
    }
    if (showAiDialog) {
        AiMealEstimateDialog(
            input = aiState.input,
            isLoading = aiState.isLoading,
            error = aiState.error,
            onInputChange = viewModel::onAiInputChanged,
            onEstimate = viewModel::estimateWithAi,
            onDismiss = { showAiDialog = false }
        )
    }

    if (showAiReviewDialog) {
        AddFoodEntryDialog(
            initialTitle = aiReviewTitle,
            initialItems = aiReviewItems,
            onDismiss = {
                showAiReviewDialog = false
                viewModel.clearAiResult()
            },
            onSave = { title, items ->
                viewModel.addManualEntry(title, items)
                showAiReviewDialog = false
                viewModel.clearAiResult()

            }

        )
    }
}
