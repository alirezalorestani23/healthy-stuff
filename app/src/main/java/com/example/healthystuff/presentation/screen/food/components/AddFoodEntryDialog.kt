package com.example.healthystuff.presentation.screen.food.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthystuff.domain.model.FoodItem
import com.example.healthystuff.ui.theme.HealthyStuffTheme


private data class FoodItemDraft(
    val name: String = "",
    val caloriesText: String = "",
    val proteinText: String = "0",
    val carbsText: String = "0",
    val fatText: String = "0"
)

@Composable
fun AddFoodEntryDialog(
    initialTitle: String = "",
    initialItems: List<FoodItem> = emptyList(),
    onDismiss: () -> Unit,
    onSave: (title: String, items: List<FoodItem>) -> Unit
) {
    var titleInput by remember(initialTitle) { mutableStateOf(initialTitle) }
    var error by remember { mutableStateOf<String?>(null) }
    val initialDrafts = remember(initialItems) {
        if (initialItems.isEmpty()) listOf(FoodItemDraft())
        else initialItems.map { FoodItemDraft(it.name, it.calories.toString(),it.proteinG.toString(),it.carbsG.toString(),it.fatG.toString()) }
    }
    var drafts by remember(initialDrafts) { mutableStateOf(initialDrafts) }

    fun trySave() {
        val title = titleInput.trim()
        if (title.isBlank()) {
            error = "Title cannot be blank"
            return
        }
        val items = drafts.mapNotNull { draft ->
            val name = draft.name.trim()
            val calories = draft.caloriesText.trim().toIntOrNull()
            val protein = draft.proteinText.trim().toIntOrNull() ?: 0
            val carbs = draft.carbsText.trim().toIntOrNull() ?: 0
            val fat = draft.fatText.trim().toIntOrNull() ?: 0


            if (name.isBlank() || calories == null || calories <= 0) return@mapNotNull null
            FoodItem(
                id = 0,
                name = name,
                quantity = null,
                unit = null,
                calories = calories,
                proteinG = protein,
                carbsG = carbs,
                fatG = fat,
                confidence = null
            )
        }

        if (items.isEmpty()) {
            error = "At least one item is required"
            return
        }


        error = null
        onSave(title, items)

    }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add meal", style = MaterialTheme.typography.titleLarge) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = titleInput,
                    onValueChange = {
                        titleInput = it
                        error = null
                    },
                    label = { Text(text = "Title") },
                    singleLine = true,
                    placeholder = { Text(text = "e.g. Avocado Toast") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(text = "Items")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(drafts) { index, draft ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = draft.name,
                                onValueChange = {
                                    drafts = drafts.toMutableList().also { list ->
                                        list[index] = list[index].copy(name = it)
                                    }
                                    error = null
                                },
                                label = { Text(text = "Name") },
                                singleLine = true,
                                placeholder = { Text(text = "e.g. Avocado") },
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = draft.caloriesText,
                                onValueChange = {
                                    drafts = drafts.toMutableList().also { list ->
                                        list[index] = list[index].copy(caloriesText = it)
                                    }
                                    error = null
                                },
                                label = { Text(text = "kcal") },
                                singleLine = true,
                                placeholder = { Text(text = "e.g. 100") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(0.6f)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = {
                                    drafts =
                                        if (drafts.size <= 1) listOf(FoodItemDraft()) else drafts.toMutableList()
                                            .also {
                                                it.removeAt(index)
                                            }
                                }
                            ) {
                                Text(text = "Remove")
                            }
                        }
                    }
                }
                Button(
                    onClick = {
                        drafts = drafts + FoodItemDraft()
                    },
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Text(text = "Add item")
                }

                error?.let { Text(it, modifier = Modifier.padding(top = 4.dp)) }
            }
        },
        confirmButton = {
            Button(onClick = ::trySave) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddFoodEntryDialogPreview() {
    HealthyStuffTheme {
        AddFoodEntryDialog(
            onDismiss = {},
            onSave = { _, _ -> }
        )
    }
}
