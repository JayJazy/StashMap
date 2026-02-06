package com.jayys.stashmap.feature.home

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.homeEntries(onBack: () -> Unit) {
    entry<HomeRoute> { _ ->
        HomeScreen()
    }
}