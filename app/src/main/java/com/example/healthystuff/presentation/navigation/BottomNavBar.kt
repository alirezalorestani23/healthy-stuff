package com.example.healthystuff.presentation.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController


@Composable
fun BottomNavBar(
    navController: NavController,
    currentRoute: String?
) {
    val tabs = Screen.bottomTabs

    android.util.Log.d(
        "BottomNav",
        "tabs=" + tabs.joinToString { it.route }
    )

    NavigationBar {
        tabs.forEach { screen ->
            val selected = currentRoute == screen.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) navController.navigate(screen.route) {
                        popUpTo(Screen.Dashboard.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(painterResource(screen.iconRes), contentDescription = null) },
                label = { Text(text = stringResource(screen.labelRes)) }
            )
        }
    }
}
