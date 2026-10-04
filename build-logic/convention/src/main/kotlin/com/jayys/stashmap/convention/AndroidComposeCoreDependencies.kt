package com.jayys.stashmap.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

private object AndroidComposeCoreLibraries {

    val moduleDependencies = listOf(
        ":core:model",
        ":core:domain"
    )

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
        "junit",
        "kotlinx.coroutines.test"
    )

    // Android test platform (BOM) — Compose UI 테스트도 같은 BOM 을 따라야 버전이 어긋나지 않음
    val androidTestPlatforms = listOf(
        "androidx.compose.bom"
    )

    // Android test dependencies
    val androidTestLibraries = listOf(
        "androidx.junit",
        "androidx.espresso.core",
        "androidx.compose.ui.test.junit4"
    )

    // Debug implementation dependencies
    val debugLibraries = listOf(
        "androidx.compose.ui.tooling",
        // createComposeRule 이 띄울 빈 Activity 를 디버그 매니페스트에 넣어줌
        "androidx.compose.ui.test.manifest"
    )
}

internal fun Project.applyAndroidComposeCoreDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        AndroidComposeCoreLibraries.moduleDependencies.forEach {
            add("implementation", project(it))
        }

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

        // Android test platform dependencies (BOM)
        AndroidComposeCoreLibraries.androidTestPlatforms.forEach { platformKey ->
            add("androidTestImplementation", platform(libs.findLibrary(platformKey).get()))
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