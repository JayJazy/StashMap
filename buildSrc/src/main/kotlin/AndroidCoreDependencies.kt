import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Android Core 모듈에 공통으로 사용되는 라이브러리 목록
 * :core:database, :core:data
 */
private object AndroidCoreLibraries {
    // Implementation dependencies
    val libraries = listOf(
        "androidx.core.ktx",
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
}

/**
 * Android Core 모듈에 공통 dependencies 적용
 * :core:database, :core:data
 */
fun Project.applyAndroidCoreDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        // Regular library dependencies
        AndroidCoreLibraries.libraries.forEach { libraryKey ->
            add("implementation", libs.findLibrary(libraryKey).get())
        }

        // KSP/Kapt dependencies
        AndroidCoreLibraries.kspLibraries.forEach { libraryKey ->
            add("ksp", libs.findLibrary(libraryKey).get())
        }

        // Test dependencies
        AndroidCoreLibraries.testLibraries.forEach { libraryKey ->
            add("testImplementation", libs.findLibrary(libraryKey).get())
        }

        // Android test library dependencies
        AndroidCoreLibraries.androidTestLibraries.forEach { libraryKey ->
            add("androidTestImplementation", libs.findLibrary(libraryKey).get())
        }
    }
}