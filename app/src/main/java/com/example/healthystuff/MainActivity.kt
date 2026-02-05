package com.example.healthystuff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.healthystuff.presentation.navigation.NavGraph
import com.example.healthystuff.ui.theme.HealthyStuffTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthyStuffTheme {
                NavGraph()
            }
        }
    }
}
