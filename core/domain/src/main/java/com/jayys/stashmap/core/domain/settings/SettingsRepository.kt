package com.jayys.stashmap.core.domain.settings

import com.jayys.stashmap.core.model.StashMapLanguage

/**
 * 앱 설정(테마/언어) 영속화 추상화.
 *
 * 도메인 타입으로 설정을 다루며, 구체적인 저장 기술은 구현체가 캡슐화한다.
 * 단순 영속화이므로 별도 UseCase 없이 호출부에서 직접 사용해도 무방하다.
 */
interface SettingsRepository {
    fun getDarkMode(): Boolean
    fun setDarkMode(isDark: Boolean)
    fun getLanguage(): StashMapLanguage
    fun setLanguage(language: StashMapLanguage)
}
