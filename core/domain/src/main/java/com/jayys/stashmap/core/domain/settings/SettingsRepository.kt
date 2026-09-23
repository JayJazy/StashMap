package com.jayys.stashmap.core.domain.settings

import com.jayys.stashmap.core.model.StashMapLanguage
import kotlinx.coroutines.flow.StateFlow

/**
 * 앱 설정(테마/언어) 추상화.
 *
 * 현재 값을 [StateFlow]로 노출해 관찰과 스냅샷 조회를 모두 지원한다.
 * `Activity.attachBaseContext`처럼 코루틴을 사용할 수 없는 프레임워크 진입점에서
 * 저장된 언어를 즉시 읽어야 하므로, 콜드 Flow가 아니라 상태를 보유하는 Flow가 요구사항이다.
 *
 * 쓰기는 I/O를 수반하므로 suspend로 선언한다. 호출부가 동기 실행을 가정하지 않으므로
 * 구현 기술(SharedPreferences, DataStore 등)을 교체해도 이 인터페이스는 바뀌지 않는다.
 */
interface SettingsRepository {
    val darkMode: StateFlow<Boolean>
    val language: StateFlow<StashMapLanguage>

    suspend fun setDarkMode(isDark: Boolean)
    suspend fun setLanguage(language: StashMapLanguage)
}
