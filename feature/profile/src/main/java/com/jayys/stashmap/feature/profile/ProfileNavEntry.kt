package com.jayys.stashmap.feature.profile

import androidx.compose.runtime.MutableState
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.profileEntries(
    isDarkMode: MutableState<Boolean>,
    onDarkModeChange: (Boolean) -> Unit
) {
    entry<ProfileRoute> { _ ->
        ProfileScreen(
            isDarkMode = isDarkMode,
            onDarkModeChange = onDarkModeChange
        )
    }
}