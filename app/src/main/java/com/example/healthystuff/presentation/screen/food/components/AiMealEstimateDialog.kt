package com.example.healthystuff.presentation.screen.food.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AiMealEstimateDialog(
    input: String,
    isLoading: Boolean,
    error: String?,
    onInputChange: (String) -> Unit,
    onEstimate: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(28.dp),
        title = { Text("AI estimate", style = MaterialTheme.typography.titleLarge) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = input,
                    onValueChange = onInputChange,
                    label = { Text("What did you eat?") },
                    placeholder = { Text("e.g. 2 eggs, toast, coffee with milk") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Text(
                    text = "Tip: include portions (\"2 eggs\", \"1 cup rice\") for better accuracy.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (isLoading) {
                    CircularProgressIndicator()
                }

                error?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }
            }
        },
        confirmButton = {
            Button(onClick = onEstimate, enabled = !isLoading) {
                Text("Estimate")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !isLoading) {
                Text("Cancel")
            }
        }
    )
}
