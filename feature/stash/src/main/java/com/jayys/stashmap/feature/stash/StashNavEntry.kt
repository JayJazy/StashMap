package com.jayys.stashmap.feature.stash

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.stashEntries(onBack: () -> Unit) {
    entry<StashRoute> {
        // 기록 등록·상세 화면이 아직 없어 전부 no-op
        StashScreen(
            onRecordClick = { },
            onFabClick = { },
        )
    }
}
