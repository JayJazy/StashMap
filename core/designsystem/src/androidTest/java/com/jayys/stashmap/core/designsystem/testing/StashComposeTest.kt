package com.jayys.stashmap.core.designsystem.testing

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.test.platform.app.InstrumentationRegistry
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme

/**
 * 모든 컴포넌트 테스트의 공통 진입점
 *
 * Stash 컴포넌트는 CompositionLocal 토큰을 읽으므로 [StashTheme] 없이 setContent 하면 터진다.
 * 다크/라이트는 색만 다르고 동작은 같아서 라이트로 고정
 */
fun ComposeContentTestRule.setStashContent(content: @Composable () -> Unit) {
    setContent {
        StashTheme(darkTheme = false) { content() }
    }
}

/** 평가 라벨은 로케일 리소스 — 하드코딩하면 기기 언어 따라 깨진다 */
fun labelOf(state: StashEvalState): String =
    InstrumentationRegistry.getInstrumentation().targetContext.getString(state.labelRes)
