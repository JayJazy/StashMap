plugins {
    id("stashmap.jvm.library")
}

dependencies {
    // src/main 에서 쓰는 테스트 라이브러리 — jvm.library 는 testImplementation 으로만 넣어서 재선언 필요
    // api: TestWatcher(상위 타입)·TestDispatcher(공개 프로퍼티)가 공개 시그니처 → 이 모듈만 걸어도 소비자가 자립한다
    api(libs.junit)
    api(libs.kotlinx.coroutines.test)
}
