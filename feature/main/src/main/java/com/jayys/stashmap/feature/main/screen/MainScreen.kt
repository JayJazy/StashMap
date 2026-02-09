package com.jayys.stashmap.feature.main.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.jayys.stashmap.feature.home.HomeRoute
import com.jayys.stashmap.feature.home.homeEntries
import com.jayys.stashmap.feature.main.ui.MainBottomBar
import com.jayys.stashmap.feature.profile.navigation.profileEntries
import com.jayys.stashmap.feature.stash.stashEntries

@Composable
fun MainScreen(startDestination: NavKey = HomeRoute) {
    val backStack = rememberNavBackStack(startDestination)
    val onBack = {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MainBottomBar(backStack = backStack)
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = onBack,
            entryProvider = entryProvider {
                homeEntries(onBack)
                stashEntries(onBack)
                profileEntries(onBack, backStack)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}