package com.example.healthystuff.presentation.screen.profile

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
fun ProfileScreen() {
    SportyBackground {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SportyHeader(
                title = "Profile",
                kicker = "Goals & settings",
                iconRes = R.drawable.ic_profile
            )

            HeroCard(
                title = "Set your goal",
                body = "Calories target and preferences will live here (portfolio-friendly polish step)."
            )
        }
    }
}
