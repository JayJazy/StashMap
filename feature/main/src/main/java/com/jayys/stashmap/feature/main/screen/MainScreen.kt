package com.jayys.stashmap.feature.main.screen

import androidx.compose.foundation.layout.consumeWindowInsets
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
            modifier = Modifier
                .padding(innerPadding)
                // 하위 화면들이 자기 Scaffold 를 또 띄운다. 여기서 소비해두지 않으면
                // 그쪽 contentWindowInsets(기본 systemBars)가 깎이지 않은 원본을 다시 읽어
                // 바텀바 높이 위에 네비게이션바 인셋이 한 번 더 얹힌다.
                // content 슬롯 안이라 bottomBar 의 인셋 처리(StashBottomNavBar)에는 영향 없음
                .consumeWindowInsets(innerPadding)
        )
    }
}