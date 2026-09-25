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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.domain.settings.SettingsRepository
import com.jayys.stashmap.core.model.StashMapLanguage
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
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
    lateinit var settingsRepository: SettingsRepository

    /**
     * 이 Activity 인스턴스가 Locale 적용에 사용한 언어.
     *
     * [attachBaseContext]에서 Locale을 적용하는 시점에 기록되며,
     * 이후 [SettingsRepository]의 최신 언어와 비교해 재생성이 필요한지 판단하는 기준이 된다.
     */
    private var appliedLanguage: StashMapLanguage? = null

    /**
     * 현재 RESUMED 구간에서 이미 재생성을 요청했는지 여부.
     *
     * [recreate]는 요청만 남기고 즉시 반환하므로, 재생성이 실제로 일어나기 전에
     * 언어가 또 바뀌면 같은 인스턴스가 [recreate]를 중복 호출할 수 있다. 그것을 막는다.
     *
     * [onPause]에서 해제한다. 관찰자가 사는 `RESUMED` 구간과 경계를 정확히 맞추면,
     * 다이얼로그나 분할화면처럼 `onStop` 없이 PAUSED에서 되돌아오는 경우에도 재시도가 가능하다. 인스턴스 수명 내내 잠가 두면 [recreate]가 반영되지 않았을 때
     * 그 인스턴스가 옛 언어로 영구히 고착되어 복구 수단이 사라지기 때문이다.
     */
    private var isRecreating = false

    /**
     * `attachBaseContext`는 `onCreate`보다 먼저 호출되어 Hilt 필드 주입이 아직 준비되지 않았다.
     * 따라서 [EntryPointAccessors]로 Application의 Hilt 컴포넌트에서 직접 [SettingsRepository]를 얻어
     * 현재 언어 스냅샷을 읽은 뒤 Locale을 적용한다.
     *
     * 읽어온 언어를 [appliedLanguage]에 기록해 두면, [onCreate]의 관찰자가
     * "지금 화면에 적용된 언어"와 "저장소의 최신 언어"를 정확히 비교할 수 있다.
     */
    final override fun attachBaseContext(newBase: Context) {
        val entryPoint = EntryPointAccessors.fromApplication(
            newBase.applicationContext,
            BaseActivityEntryPoint::class.java
        )
        val language = entryPoint.settingsRepository().language.value
        appliedLanguage = language
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
        // Hilt 필드 주입은 super.onCreate()에서 일어나므로
        // settingsRepository를 사용하는 코드는 반드시 이 호출 이후에 와야 한다.
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        observeLanguageChange()

        setContent {
            val isDarkMode by settingsRepository.darkMode.collectAsStateWithLifecycle()

            StashTheme(darkTheme = isDarkMode) {
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

    /**
     * 언어 설정 변경을 관찰해 Activity를 재생성한다.
     *
     * 언어는 테마와 달리 Compose 상태가 아니라 `Context`의 `Configuration`에 묶여 있어,
     * 이미 만들어진 리소스에 새 Locale을 적용하려면 Activity 재생성이 필요하다.
     *
     * 재생성 시점을 **호출부가 직접 결정하지 않는 이유**:
     * `SettingsRepository.setLanguage`는 suspend 함수라 저장 완료 시점이 비동기다.
     * 화면에서 저장 요청 직후 `recreate()`를 호출하면 저장이 끝나기 전에 새 Activity가 만들어져
     * `attachBaseContext`가 **이전 언어**를 읽는 경쟁 조건이 발생한다.
     * 따라서 "저장이 끝나 상태가 실제로 바뀐 것"을 신호로 삼아 여기서만 재생성한다.
     *
     * 무한 루프가 생기지 않는 이유:
     * 재생성된 새 인스턴스의 [attachBaseContext]가 최신 언어를 [appliedLanguage]에 기록하므로,
     * 새 인스턴스의 관찰자가 같은 값을 받아도 조건이 더 이상 성립하지 않는다.
     * 이는 [SettingsRepository]가 **구독자 없이도 현재 값을 돌려주는** 상태 보유 Flow라는 전제에 기댄다.
     * 해당 제약은 [SettingsRepository] 문서에 명시되어 있다.
     *
     * `drop(1)` 대신 [appliedLanguage] 비교를 쓰는 이유:
     * 방출 횟수가 아니라 "실제로 적용된 값"을 기준으로 판단하므로,
     * 저장(I/O)이 끝나기 전에 앱이 백그라운드로 내려가 상태 변경이 RESUMED 밖에서 일어나도,
     * 포그라운드로 돌아온 시점에 정확히 재생성된다.
     * `drop(1)`이었다면 관찰이 재개될 때 들어오는 현재 값을 첫 방출로 여겨 흘려보낸다.
     *
     * `STARTED`가 아니라 `RESUMED`를 쓰는 이유:
     * 다른 화면이 위에 떠 있어 보이기만 하는 상태에서 재생성하면 사용자에게 깜빡임이 보인다.
     * 포그라운드에 완전히 올라온 뒤로 재생성을 미룬다.
     * (`RESUMED`로 올려도 [lifecycleScope]가 `Main.immediate`인 이상 [recreate]는 여전히
     * 생명주기 콜백 처리 도중 동기 호출된다. 상태 등급은 호출 시점을 옮길 뿐 그것을 없애지 못한다.
     * [recreate]는 요청만 큐잉하고 즉시 반환하므로 이 동기 호출 자체는 문제가 되지 않는다.)
     */
    private fun observeLanguageChange() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                settingsRepository.language.collect { language ->
                    val applied = appliedLanguage ?: return@collect
                    if (language == applied) return@collect
                    if (isFinishing || isDestroyed || isRecreating) return@collect

                    isRecreating = true
                    recreate()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // 재생성 요청은 현재 RESUMED 구간에서만 유효하다.
        // 관찰자도 이 시점에 취소되므로 해제로 인한 재진입은 없고,
        // 다시 RESUMED가 됐을 때 언어가 여전히 어긋나 있으면 재시도할 수 있다.
        isRecreating = false
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
