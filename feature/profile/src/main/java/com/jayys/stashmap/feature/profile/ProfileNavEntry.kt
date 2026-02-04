package com.jayys.stashmap.feature.profile

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.profileEntries() {
    entry<ProfileRoute> { _ ->
        ProfileScreen()
    }
}