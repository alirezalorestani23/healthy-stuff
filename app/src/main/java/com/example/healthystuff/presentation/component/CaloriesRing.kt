package com.example.healthystuff.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.min
import androidx.compose.material3.Text

@Composable
fun CaloriesRing(
    consumedCalories: Int,
    targetCalories: Int,
    modifier: Modifier = Modifier
) {
    val safeTarget = targetCalories.coerceAtLeast(1)
    val progressRaw = (consumedCalories.toFloat() / safeTarget).coerceIn(0f, 1f)
    val progress by animateFloatAsState(
        targetValue = progressRaw,
        animationSpec = tween(durationMillis = 650),
        label = "calories_progress"
    )

    val trackColor = MaterialTheme.colorScheme.surfaceVariant
    val progressBrush = Brush.sweepGradient(
        0.00f to MaterialTheme.colorScheme.primary,
        0.55f to MaterialTheme.colorScheme.secondary,
        1.00f to MaterialTheme.colorScheme.tertiary
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(118.dp)) {
            val strokeWidth = 14.dp.toPx()
            val diameter = min(size.width, size.height)
            val topLeft = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
            val arcSize = Size(diameter, diameter)
            val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Round)

            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke
            )
            drawArc(
                brush = progressBrush,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = consumedCalories.toString(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = "kcal",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
