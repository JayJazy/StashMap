package com.jayys.stashmap.feature.testing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * `viewModelScope` 의 Main 디스패처를 테스트 디스패처로 교체하는 JUnit4 Rule
 *
 * 기본값 [StandardTestDispatcher] 는 코루틴을 즉시 실행하지 않고 큐에 쌓음
 * → "비동기 작업 완료 전에 상태를 읽으면 옛 값" 이라는 회귀 성질을 그대로 재현
 * → `advanceUntilIdle()` 로 완료 시점을 명시적으로 통제
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
