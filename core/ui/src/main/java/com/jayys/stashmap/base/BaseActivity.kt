package com.jayys.stashmap.base

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.stashmap.core.common.local.AppSettingsManager
import com.jayys.stashmap.core.common.local.LocaleHelper
import com.jayys.stashmap.core.designsystem.theme.StashMapTheme
import com.jayys.stashmap.core.domain.settings.SettingsRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

/**
 * 모든 Activity의 기본 클래스
 *
 * Edge-to-Edge를 지원하며 시스템 바(상태바, 네비게이션바) 패딩을 자동으로 적용
 *
 * ## 기본 사용법
 * ```kotlin
 * class MainActivity : BaseActivity() {
 *     @Composable
 *     override fun Screen() {
 *         MainScreen()
 *     }
 * }
 * ```
 *
 * ## WindowInsets 커스터마이징
 * 특정 Activity에서 시스템 바 패딩을 다르게 적용하고 싶다면 [getWindowInsets] 메서드 오버라이드
 *
 * ### 예시 1: 상태바만 패딩 적용 (네비게이션바는 겹치게)
 * ```kotlin
 * class FullscreenVideoActivity : BaseActivity() {
 *     @Composable
 *     override fun getWindowInsets(): List<WindowInsets> {
 *         return listOf(WindowInsets.statusBars)
 *     }
 *
 *     @Composable
 *     override fun Screen() {
 *         VideoPlayerScreen()
 *     }
 * }
 * ```
 *
 * ### 예시 2: 완전한 전체화면 (모든 패딩 제거)
 * ```kotlin
 * class ImmersiveActivity : BaseActivity() {
 *     @Composable
 *     override fun getWindowInsets(): List<WindowInsets> {
 *         return emptyList()
 *     }
 *
 *     @Composable
 *     override fun Screen() {
 *         ImmersiveGameScreen()
 *     }
 * }
 * ```
 *
 * ### 예시 3: 네비게이션바만 패딩
 * ```kotlin
 * class BottomSheetActivity : BaseActivity() {
 *     @Composable
 *     override fun getWindowInsets(): List<WindowInsets> {
 *         return listOf(WindowInsets.navigationBars)
 *     }
 *
 *     @Composable
 *     override fun Screen() {
 *         BottomSheetScreen()
 *     }
 * }
 * ```
 *
 * ### 예시 4: 키보드까지 포함한 커스텀 조합
 * ```kotlin
 * class ChatActivity : BaseActivity() {
 *     @Composable
 *     override fun getWindowInsets(): List<WindowInsets> {
 *         return listOf(
 *             WindowInsets.statusBars,
 *             WindowInsets.ime  // 키보드
 *         )
 *     }
 *
 *     @Composable
 *     override fun Screen() {
 *         ChatScreen()
 *     }
 * }
 * ```
 */
abstract class BaseActivity : ComponentActivity() {

    @Inject
    lateinit var appSettingsManager: AppSettingsManager

    /**
     * `attachBaseContext`는 `onCreate`보다 먼저 호출되어 Hilt 필드 주입이 아직 준비되지 않았다.
     * 따라서 [EntryPointAccessors]로 Application의 Hilt 컴포넌트에서 직접 [SettingsRepository]를 얻어
     * 저장된 언어를 추상화를 경유해 읽은 뒤 Locale을 적용한다.
     */
    override fun attachBaseContext(newBase: Context) {
        val entryPoint = EntryPointAccessors.fromApplication(
            newBase.applicationContext,
            BaseActivityEntryPoint::class.java
        )
        val language = entryPoint.settingsRepository().getLanguage()
        super.attachBaseContext(LocaleHelper.wrap(newBase, language))
    }

    /**
     * 패딩을 적용할 WindowInsets를 반환
     *
     * 기본값: 상태바 + 네비게이션바 모두 적용
     *
     * 특정 Activity에서 다른 패딩 설정이 필요한 경우 오버라이드
     */
    @Composable
    protected open fun getWindowInsets(): List<WindowInsets> {
        return listOf(WindowInsets.statusBars, WindowInsets.navigationBars)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkMode by appSettingsManager.isDarkMode.collectAsStateWithLifecycle()

            StashMapTheme(isDarkMode = isDarkMode) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .applyWindowInsets(getWindowInsets())
                ) {
                    Screen()
                }
            }
        }
    }

    private fun Modifier.applyWindowInsets(insets: List<WindowInsets>): Modifier {
        return insets.fold(this) { modifier, inset ->
            modifier.windowInsetsPadding(inset)
        }
    }

    @Composable
    abstract fun Screen()
}

/**
 * `attachBaseContext`(Hilt 필드 주입 이전 시점)에서 Application 컴포넌트의 싱글톤에 접근하기 위한 EntryPoint.
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface BaseActivityEntryPoint {
    fun settingsRepository(): SettingsRepository
}
