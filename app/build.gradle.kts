plugins {
    id("stashmap.android.application")
}

android {
    namespace = "com.jayys.stashmap"

    defaultConfig {
        // Hilt 계측 테스트는 HiltTestApplication 을 띄워야 함 → 컨벤션 기본 러너를 앱 전용으로 덮어씀
        testInstrumentationRunner = "com.jayys.stashmap.HiltTestRunner"
    }
}

dependencies {
    implementation(project(":feature:main"))
    implementation(project(":core:data"))

    // App specific dependencies
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Hilt instrumentation testing (app 전용 — feature 모듈로 번지지 않게 여기에 둠)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.test.runner)
    kspAndroidTest(libs.hilt.compiler)
}
