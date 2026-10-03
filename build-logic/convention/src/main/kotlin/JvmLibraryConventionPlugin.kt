import com.jayys.stashmap.convention.applyKotlinJvmCoreDependencies
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            // 공개 시그니처에 노출되는 의존성을 api 로 전파하기 위해 필요하다.
            // kotlin("jvm") 은 java 플러그인만 적용하므로 api 컨피규레이션이 생기지 않는다.
            pluginManager.apply("java-library")

            extensions.configure<JavaPluginExtension> {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }

            applyKotlinJvmCoreDependencies()
        }
    }
}
