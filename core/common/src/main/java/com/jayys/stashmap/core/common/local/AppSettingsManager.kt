package com.jayys.stashmap.core.common.local

import com.jayys.stashmap.core.domain.settings.SettingsRepository
import com.jayys.stashmap.core.model.StashMapLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 앱 전역 설정(테마/언어)의 **단일 진실 공급원(Single Source of Truth)**.
 *
 * 메모리 상의 반응형 상태(StateFlow)를 보관하고, 영속화는 [SettingsRepository]에 위임한다.
 * 저장과 상태 갱신을 이 클래스 한 곳에서만 수행하므로 이중 쓰기(split-brain)가 발생하지 않는다.
 *
 * 전역 `object`가 아닌 `@Singleton` 클래스로 DI를 통해 주입되므로 테스트 시 교체/격리가 가능하다.
 * 프레임워크(Locale/Configuration) 처리는 [LocaleHelper]가 담당한다.
 */
@Singleton
class AppSettingsManager @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    // 생성 시점에 저장된 값을 읽어 초기 상태를 구성한다.
    private val _isDarkMode = MutableStateFlow(settingsRepository.getDarkMode())
    val isDarkMode = _isDarkMode.asStateFlow()

    private val _stashLanguage = MutableStateFlow(settingsRepository.getLanguage())
    val stashLanguage = _stashLanguage.asStateFlow()

    fun setDarkMode(isDark: Boolean) {
        settingsRepository.setDarkMode(isDark)
        _isDarkMode.value = isDark
    }

    fun setLanguage(language: StashMapLanguage) {
        settingsRepository.setLanguage(language)
        _stashLanguage.value = language
    }
}
