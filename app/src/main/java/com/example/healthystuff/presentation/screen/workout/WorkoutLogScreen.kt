package com.example.healthystuff.presentation.screen.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.healthystuff.R
import com.example.healthystuff.presentation.component.HeroCard
import com.example.healthystuff.presentation.component.SportyBackground
import com.example.healthystuff.presentation.component.SportyHeader

@Composable
fun WorkoutLogScreen() {
    SportyBackground {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SportyHeader(
                title = "Workout",
                kicker = "Training log",
                iconRes = R.drawable.ic_workout
            )

            HeroCard(
                title = "No workouts yet",
                body = "We’ll add workout entry + AI plan generation next."
            )
        }
    }
}
