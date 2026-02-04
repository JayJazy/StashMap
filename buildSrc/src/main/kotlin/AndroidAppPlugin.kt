import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

class AndroidAppPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("com.android.application")
        project.pluginManager.apply("org.jetbrains.kotlin.android")
        project.pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
        project.pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

        project.extensions.configure(BaseAppModuleExtension::class.java) {
            compileSdk = StashMapConfig.compileSdk

            defaultConfig {
                applicationId = StashMapConfig.applicationId
                minSdk = StashMapConfig.minSdk
                targetSdk = StashMapConfig.compileSdk
                versionCode = StashMapConfig.versionCode
                versionName = StashMapConfig.versionName

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }

            buildFeatures {
                compose = true
            }

            project.tasks.withType(KotlinJvmCompile::class.java).configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.fromTarget(StashMapConfig.jvmTarget))
                }
            }
        }
    }
}