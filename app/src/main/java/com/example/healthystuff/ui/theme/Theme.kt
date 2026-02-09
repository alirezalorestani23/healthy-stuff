package com.example.healthystuff.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = NeonGreen,
    secondary = ElectricBlue,
    tertiary = EnergyOrange,
    background = Carbon,
    surface = Carbon2,
    surfaceVariant = Color(0xFF1B2536),
    onPrimary = Carbon,
    onSecondary = Color.White,
    onTertiary = Carbon,
    onBackground = OffWhite,
    onSurface = OffWhite,
    onSurfaceVariant = Color(0xFFB8C6E0)
)

private val LightColorScheme = lightColorScheme(
    primary = NeonGreen2,
    secondary = ElectricBlue,
    tertiary = EnergyOrange,
    background = OffWhite,
    surface = Color.White,
    surfaceVariant = Color(0xFFE9EEF7),
    onPrimary = Carbon,
    onSecondary = Color.White,
    onTertiary = Carbon,
    onBackground = Ink,
    onSurface = Ink,
    onSurfaceVariant = Slate

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun HealthyStuffTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
