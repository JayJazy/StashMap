import com.android.build.api.dsl.ApplicationExtension
import com.jayys.stashmap.convention.StashMapConfig
import com.jayys.stashmap.convention.applyFeatureDependencies
import com.jayys.stashmap.convention.configureAndroidCompose
import com.jayys.stashmap.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * :app
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("com.google.devtools.ksp")
                apply("com.google.dagger.hilt.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureAndroidCompose(this)

                defaultConfig {
                    applicationId = StashMapConfig.applicationId
                    targetSdk = StashMapConfig.targetSdk
                    versionCode = StashMapConfig.versionCode
                    versionName = StashMapConfig.versionName
                }
            }

            applyFeatureDependencies()
        }
    }
}
