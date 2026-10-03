package com.jayys.stashmap

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Hilt 계측 테스트용 [AndroidJUnitRunner]
 *
 * - 실제 [StashMapApp] 대신 [HiltTestApplication]을 띄움 → `@UninstallModules`/`@BindValue` 사용 가능
 * - `app/build.gradle.kts`의 `testInstrumentationRunner`가 이 클래스를 지정
 */
class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
