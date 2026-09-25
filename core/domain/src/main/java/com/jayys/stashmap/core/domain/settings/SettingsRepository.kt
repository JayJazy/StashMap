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
 * **구현 제약**: [darkMode]와 [language]는 구독자가 없어도 현재 값을 돌려주는
 * 상태 보유 Flow여야 한다. `BaseActivity.attachBaseContext`가 구독 없이 `.value`로
 * 현재 언어를 읽기 때문이다. [darkMode]도 마찬가지로, 첫 컴포지션이 `initialValue`로 그려지면
 * 테마가 한 번 깜빡인다.
 *
 * `stateIn(scope, SharingStarted.WhileSubscribed(...), initialValue)`로 구현하면
 * 콜드 스타트 직후(구독자가 한 번도 붙지 않은 시점)에 `.value`가 `initialValue`를 돌려주므로,
 * Activity가 저장된 언어가 아니라 기본값으로 만들어졌다가 뒤늦게 재생성된다.
 * `replayExpirationMillis = 0`으로 두면 더 나쁘다. 구독이 끊길 때마다 캐시가 만료되어
 * 앱을 잠깐 백그라운드로 내렸다 올 때마다 기본값으로 시작한다.
 *
 * 타입(`StateFlow`)은 이 제약을 강제하지 못한다. 문서로만 유지되는 규약이다.
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
