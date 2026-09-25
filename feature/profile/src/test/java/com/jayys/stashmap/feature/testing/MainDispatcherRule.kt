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
 * `viewModelScope`가 쓰는 Main 디스패처를 테스트 디스패처로 교체하는 JUnit4 Rule.
 *
 * 기본값인 [StandardTestDispatcher]는 코루틴을 **즉시 실행하지 않고 큐에 쌓는다**.
 * 덕분에 "비동기 작업이 끝나기 전에 상태를 읽으면 옛 값이 보인다"는 이번 회귀의 성질을
 * 테스트에서 그대로 재현하고, `advanceUntilIdle()`로 완료 시점을 명시적으로 통제할 수 있다.
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
