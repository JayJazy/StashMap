import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Android Compose Core 모듈에 공통으로 사용되는 라이브러리 목록
 * :core:ui, :core:designsystem
 */
private object AndroidComposeCoreLibraries {
    // Platform dependencies (BOM)
    val platforms = listOf(
        "androidx.compose.bom"
    )

    // Implementation dependencies
    val libraries = listOf(
        "androidx.core.ktx",
        "androidx.compose.ui",
        "androidx.compose.ui.graphics",
        "androidx.compose.ui.tooling.preview",
        "androidx.compose.material3",
        "hilt.android"
    )

    // KSP/Kapt dependencies
    val kspLibraries = listOf(
        "hilt.compiler"
    )

    // Test dependencies
    val testLibraries = listOf(
        "junit"
    )

    // Android test dependencies
    val androidTestLibraries = listOf(
        "androidx.junit",
        "androidx.espresso.core"
    )

    // Debug implementation dependencies
    val debugLibraries = listOf(
        "androidx.compose.ui.tooling"
    )
}

/**
 * Android Compose Core 모듈에 공통 dependencies 적용
 * :core:ui, :core:designsystem
 */
fun Project.applyAndroidComposeCoreDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        // Platform dependencies (BOM)
        AndroidComposeCoreLibraries.platforms.forEach { platformKey ->
            add("implementation", platform(libs.findLibrary(platformKey).get()))
        }

        // Regular library dependencies
        AndroidComposeCoreLibraries.libraries.forEach { libraryKey ->
            add("implementation", libs.findLibrary(libraryKey).get())
        }

        // KSP/Kapt dependencies
        AndroidComposeCoreLibraries.kspLibraries.forEach { libraryKey ->
            add("ksp", libs.findLibrary(libraryKey).get())
        }

        // Test dependencies
        AndroidComposeCoreLibraries.testLibraries.forEach { libraryKey ->
            add("testImplementation", libs.findLibrary(libraryKey).get())
        }

        // Android test library dependencies
        AndroidComposeCoreLibraries.androidTestLibraries.forEach { libraryKey ->
            add("androidTestImplementation", libs.findLibrary(libraryKey).get())
        }

        // Debug library dependencies
        AndroidComposeCoreLibraries.debugLibraries.forEach { libraryKey ->
            add("debugImplementation", libs.findLibrary(libraryKey).get())
        }
    }
}