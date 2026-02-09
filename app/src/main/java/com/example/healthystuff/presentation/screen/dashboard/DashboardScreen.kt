package com.example.healthystuff.presentation.screen.dashboard

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
fun DashboardScreen() {
    SportyBackground {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SportyHeader(
                title = "Dashboard",
                kicker = "Weekly momentum",
                iconRes = R.drawable.ic_dashboard
            )

            HeroCard(
                title = "Build your streak",
                body = "Log meals and workouts to unlock your daily overview here."
            )

            HeroCard(
                title = "Next up",
                body = "1. Workout templates\n2. Goal-based calorie target\n3. AI coach suggestions"
            )
        }
    }
}
