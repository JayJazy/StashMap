package com.jayys.stashmap.feature.testing

import com.jayys.stashmap.core.domain.settings.SettingsRepository
import com.jayys.stashmap.core.model.StashMapLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 테스트용 인메모리 [SettingsRepository] 구현.
 *
 * 실제 구현과 동일하게 "쓰기 → 상태 갱신"이 한 번에 일어나도록 맞춘다.
 * 호출 여부/인자를 기록해 ViewModel이 저장소에 제대로 위임하는지 검증한다.
 */
class FakeSettingsRepository(
    initialLanguage: StashMapLanguage = StashMapLanguage.KOREAN,
    initialDarkMode: Boolean = false
) : SettingsRepository {

    private val _language = MutableStateFlow(initialLanguage)
    override val language: StateFlow<StashMapLanguage> = _language.asStateFlow()

    private val _darkMode = MutableStateFlow(initialDarkMode)
    override val darkMode: StateFlow<Boolean> = _darkMode.asStateFlow()

    /** `setLanguage` 호출 횟수. */
    var setLanguageCallCount: Int = 0
        private set

    /** 마지막으로 전달된 언어. 한 번도 호출되지 않았으면 null. */
    var lastSetLanguage: StashMapLanguage? = null
        private set

    override suspend fun setLanguage(language: StashMapLanguage) {
        setLanguageCallCount++
        lastSetLanguage = language
        _language.value = language
    }

    override suspend fun setDarkMode(isDark: Boolean) {
        _darkMode.value = isDark
    }
}
