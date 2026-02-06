package com.jayys.stashmap.feature.stash

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.stashEntries(onBack: () -> Unit) {
    entry<StashRoute> {
        StashScreen()
    }
}