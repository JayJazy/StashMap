package com.jayys.stashmap.core.common.di

import javax.inject.Qualifier

/**
 * 주입받을 디스패처를 지정하는 퀄리파이어
 *
 * `Dispatchers.IO` 를 코드에 박으면 테스트가 실제 스레드 풀을 타서 가상 시간을 못 쓴다
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: StashMapDispatchers)

/** 값을 늘릴 때는 [DispatchersModule] 에 프로바이더를 함께 추가할 것 — 없으면 주입 지점에서 컴파일 실패 */
enum class StashMapDispatchers {
    IO,
}
