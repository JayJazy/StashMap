package com.jayys.stashmap.feature.main.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.jayys.stashmap.feature.home.HomeRoute
import com.jayys.stashmap.feature.home.homeEntries
import com.jayys.stashmap.feature.main.ui.MainBottomBar
import com.jayys.stashmap.feature.profile.profileEntries
import com.jayys.stashmap.feature.stash.stashEntries

@Composable
fun MainScreen(
    isDarkMode: MutableState<Boolean>,
    onDarkModeChange: (Boolean) -> Unit,
    startDestination: NavKey = HomeRoute
) {
    val backStack = rememberNavBackStack(startDestination)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MainBottomBar(backStack = backStack)
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = {
                if (backStack.size > 1) {
                    backStack.removeLastOrNull()
                }
            },
            entryProvider = entryProvider {
                homeEntries()
                stashEntries()
                profileEntries(isDarkMode, onDarkModeChange)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}