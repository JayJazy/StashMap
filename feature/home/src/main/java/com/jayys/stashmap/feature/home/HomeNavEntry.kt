package com.jayys.stashmap.feature.home

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

fun EntryProviderScope<NavKey>.homeEntries(onBack: () -> Unit) {
    entry<HomeRoute> { _ ->
        // 기록 등록·목록 화면이 아직 없어 전부 no-op
        HomeScreen(
            onQuickRecordClick = { },
            onRecordClick = { },
            onSeeAllRecentClick = { },
            onSeeAllWishlistClick = { },
            onFabClick = { },
        )
    }
}
