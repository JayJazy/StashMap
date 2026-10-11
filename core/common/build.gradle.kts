plugins {
    id("stashmap.android.library")
}

android {
    namespace = "com.jayys.stashmap.core.common"
}

dependencies {
    // DispatchersModule 이 CoroutineDispatcher 를 공개 시그니처로 내보낸다 → api
    api(libs.kotlinx.coroutines.core)
}
