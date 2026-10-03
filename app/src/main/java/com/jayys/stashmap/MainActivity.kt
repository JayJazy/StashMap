package com.jayys.stashmap

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.domain.settings.SettingsRepository
import com.jayys.stashmap.core.model.StashMapLanguage
import com.jayys.stashmap.feature.main.screen.MainScreen
import com.jayys.stashmap.locale.LocaleHelper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var settingsRepository: SettingsRepository

    /** Locale에 실제로 적용한 언어 — 저장소 최신 값과 비교할 기준 */
    private var appliedLanguage: StashMapLanguage? = null

    /** recreate()는 요청만 큐잉 → 중복 호출 방지. onPause에서 해제 — 계속 잠가두면 옛 언어로 고착 */
    private var isRecreating = false

    // onCreate보다 먼저 호출 → Hilt 필드 주입 불가, EntryPoint로 직접 획득
    // 이 시점엔 applicationContext 없음 → newBase 사용, by lazy로는 표현 불가
    override fun attachBaseContext(newBase: Context) {
        val entryPoint = EntryPointAccessors.fromApplication(
            newBase.applicationContext,
            MainActivityEntryPoint::class.java
        )
        settingsRepository = entryPoint.settingsRepository()
        val language = settingsRepository.language.value
        appliedLanguage = language
        super.attachBaseContext(LocaleHelper.wrap(newBase, language))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        observeLanguageChange()

        setContent {
            val isDarkMode by settingsRepository.darkMode.collectAsStateWithLifecycle()

            StashTheme(
                darkTheme = isDarkMode
            ) {
                // 셸에서 인셋 소비 금지 — 소비 시 MainBottomBar의 navigationBarsPadding()이 0이 돼
                // 바텀바가 네비게이션바 뒤로 배경을 확장할 수 없음
                MainScreen()
            }
        }
    }

    // 언어는 Configuration에 묶임 → Activity 재생성 필요
    // setLanguage는 suspend → 화면에서 직접 recreate() 시 attachBaseContext가 이전 언어 읽음
    // 저장 완료 후의 상태 변경을 재생성 신호로 사용
    // drop(1) 대신 appliedLanguage 비교 — 백그라운드 변경 누락 방지
    // RESUMED 사용 — STARTED면 다른 화면이 위에 있을 때 재생성돼 깜빡임 발생
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
        isRecreating = false
    }
}

/** attachBaseContext는 Hilt 주입 전 → Application 컴포넌트에서 직접 획득 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface MainActivityEntryPoint {
    fun settingsRepository(): SettingsRepository
}
