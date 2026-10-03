package com.jayys.stashmap.core.domain.settings

import com.jayys.stashmap.core.model.StashMapLanguage
import kotlinx.coroutines.flow.StateFlow

/**
 * 앱 설정(테마/언어) 추상화
 *
 * 구현 제약 — 구독자 없이도 `.value` 가 저장된 값을 돌려줘야 함
 * `attachBaseContext` 가 구독 없이 언어를 읽음 → `stateIn(WhileSubscribed)` 로 만들면
 * 콜드 스타트에 기본값이 나와 Activity 가 옛 언어로 떴다가 재생성
 */
interface SettingsRepository {
    val darkMode: StateFlow<Boolean>
    val language: StateFlow<StashMapLanguage>

    suspend fun setDarkMode(isDark: Boolean)
    suspend fun setLanguage(language: StashMapLanguage)
}
