import com.android.build.api.dsl.LibraryExtension
import com.jayys.stashmap.convention.StashMapConfig
import com.jayys.stashmap.convention.applyAndroidComposeCoreDependencies
import com.jayys.stashmap.convention.configureAndroidCompose
import com.jayys.stashmap.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("com.google.devtools.ksp")
                apply("com.google.dagger.hilt.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureAndroidCompose(this)
                defaultConfig.consumerProguardFiles("consumer-rules.pro")

                // 라이브러리는 targetSdk 를 안 쓰므로 androidTest APK 가 minSdk(24) 를 타깃하게 된다.
                // 그러면 API 35 기기가 "구버전 앱" 경고 다이얼로그를 띄워 테스트 Activity 를 PAUSED 로 밀어내고,
                // Compose 테스트는 RESUMED 루트만 보기 때문에 "No compose hierarchies found" 로 전부 실패한다.
                // 테스트 APK 에만 적용되고 배포되는 AAR 에는 영향 없음
                testOptions.targetSdk = StashMapConfig.targetSdk
            }

            applyAndroidComposeCoreDependencies()
        }
    }
}
