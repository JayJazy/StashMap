package com.jayys.stashmap.feature.testing

import com.jayys.stashmap.core.domain.settings.SettingsRepository
import com.jayys.stashmap.core.model.StashMapLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 테스트용 인메모리 [SettingsRepository] 구현
 *
 * - 실제 구현과 동일하게 "쓰기 → 상태 갱신" 이 한 번에 일어나도록 맞춤
 * - 호출 여부/인자를 기록 → ViewModel 의 위임 여부 검증
 */
class FakeSettingsRepository(
    initialLanguage: StashMapLanguage = StashMapLanguage.KOREAN,
    initialDarkMode: Boolean = false
) : SettingsRepository {

    private val _language = MutableStateFlow(initialLanguage)
    override val language: StateFlow<StashMapLanguage> = _language.asStateFlow()

    private val _darkMode = MutableStateFlow(initialDarkMode)
    override val darkMode: StateFlow<Boolean> = _darkMode.asStateFlow()

    /** `setLanguage` 호출 횟수 */
    var setLanguageCallCount: Int = 0
        private set

    /** 마지막으로 전달된 언어 — 미호출이면 null */
    var lastSetLanguage: StashMapLanguage? = null
        private set

    /** `setDarkMode` 호출 횟수 */
    var setDarkModeCallCount: Int = 0
        private set

    /** 마지막으로 전달된 다크 모드 값 — 미호출이면 null */
    var lastSetDarkMode: Boolean? = null
        private set

    override suspend fun setLanguage(language: StashMapLanguage) {
        setLanguageCallCount++
        lastSetLanguage = language
        _language.value = language
    }

    override suspend fun setDarkMode(isDark: Boolean) {
        setDarkModeCallCount++
        lastSetDarkMode = isDark
        _darkMode.value = isDark
    }
}
