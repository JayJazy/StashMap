import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Feature 모듈에 공통으로 사용되는 라이브러리 목록
 * libs.versions.toml의 [libraries] 섹션에 정의된 키를 사용
 */
private object FeatureLibraries {
    // Core modules
    val coreModules = listOf(
        ":core:designsystem",
        ":core:ui",
        ":core:domain",
        ":core:model"
    )

    // Platform dependencies (BOM)
    val platforms = listOf(
        "androidx.compose.bom"
    )

    // Implementation dependencies
    val libraries = listOf(
        "androidx.core.ktx",
        "androidx.appcompat",
        "androidx.compose.ui",
        "androidx.compose.ui.graphics",
        "androidx.compose.ui.tooling.preview",
        "androidx.compose.material3",
        "androidx.navigation3.runtime",
        "androidx.navigation3.ui",
        "kotlinx.serialization.core",
        "hilt.android",
        "hilt.navigation.compose"
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
        "androidx.espresso.core",
        "androidx.compose.ui.test.junit4"
    )

    // Android test platforms
    val androidTestPlatforms = listOf(
        "androidx.compose.bom"
    )

    // Debug implementation dependencies
    val debugLibraries = listOf(
        "androidx.compose.ui.tooling",
        "androidx.compose.ui.test.manifest"
    )
}

/**
 * Feature 모듈에 공통 dependencies 적용
 */
fun Project.applyFeatureDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        // Core module dependencies
        FeatureLibraries.coreModules.forEach { module ->
            add("implementation", project(module))
        }

        // Platform dependencies (BOM)
        FeatureLibraries.platforms.forEach { platformKey ->
            add("implementation", platform(libs.findLibrary(platformKey).get()))
        }

        // Regular library dependencies
        FeatureLibraries.libraries.forEach { libraryKey ->
            add("implementation", libs.findLibrary(libraryKey).get())
        }

        // KSP/Kapt dependencies
        FeatureLibraries.kspLibraries.forEach { libraryKey ->
            add("ksp", libs.findLibrary(libraryKey).get())
        }

        // Test dependencies
        FeatureLibraries.testLibraries.forEach { libraryKey ->
            add("testImplementation", libs.findLibrary(libraryKey).get())
        }

        // Android test platform dependencies
        FeatureLibraries.androidTestPlatforms.forEach { platformKey ->
            add("androidTestImplementation", platform(libs.findLibrary(platformKey).get()))
        }

        // Android test library dependencies
        FeatureLibraries.androidTestLibraries.forEach { libraryKey ->
            add("androidTestImplementation", libs.findLibrary(libraryKey).get())
        }

        // Debug library dependencies
        FeatureLibraries.debugLibraries.forEach { libraryKey ->
            add("debugImplementation", libs.findLibrary(libraryKey).get())
        }
    }
}