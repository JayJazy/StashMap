package com.jayys.stashmap.feature.main.base

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
import androidx.compose.ui.Modifier
import com.jayys.stashmap.core.designsystem.theme.StashMapTheme

/**
 * 모든 Activity의 기본 클래스
 *
 * Edge-to-Edge를 지원하며 시스템 바(상태바, 네비게이션바) 패딩을 자동으로 적용합니다.
 *
 * ## 기본 사용법
 * ```kotlin
 * class MainActivity : BaseActivity() {
 *     @Composable
 *     override fun Content() {
 *         MainScreen()
 *     }
 * }
 * ```
 *
 * ## WindowInsets 커스터마이징
 * 특정 Activity에서 시스템 바 패딩을 다르게 적용하고 싶다면 [getWindowInsets] 메서드를 오버라이드하세요.
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
 *     override fun Content() {
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
 *     override fun Content() {
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
 *     override fun Content() {
 *         BottomSheetContent()
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
 *     override fun Content() {
 *         ChatScreen()
 *     }
 * }
 * ```
 */
abstract class BaseActivity : ComponentActivity() {

    /**
     * 패딩을 적용할 WindowInsets를 반환합니다.
     *
     * 기본값: 상태바 + 네비게이션바 모두 적용
     *
     * 특정 Activity에서 다른 패딩 설정이 필요한 경우 오버라이드하세요.
     */
    @Composable
    protected open fun getWindowInsets(): List<WindowInsets> {
        return listOf(WindowInsets.statusBars, WindowInsets.navigationBars)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StashMapTheme {
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
        var modifier = this
        insets.forEach { inset ->
            modifier = modifier.windowInsetsPadding(inset)
        }
        return modifier
    }

    @Composable
    abstract fun Screen()
}