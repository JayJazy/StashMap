import com.android.build.api.dsl.LibraryExtension
import com.jayys.stashmap.convention.applyFeatureDependencies
import com.jayys.stashmap.convention.configureAndroidCompose
import com.jayys.stashmap.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidFeatureConventionPlugin : Plugin<Project> {
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
            }

            applyFeatureDependencies()
        }
    }
}
