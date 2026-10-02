package com.jayys.stashmap

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Hilt 계측 테스트용 [AndroidJUnitRunner].
 *
 * 계측 테스트에서는 `@HiltAndroidApp`이 붙은 실제 [StashMapApp] 대신
 * Hilt가 생성한 [HiltTestApplication]을 Application으로 띄워야 한다.
 * 그래야 테스트에서 `@UninstallModules`/`@BindValue`로 의존성을 교체할 수 있다.
 *
 * `android.defaultConfig.testInstrumentationRunner`가 이 클래스를 가리킨다.
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
