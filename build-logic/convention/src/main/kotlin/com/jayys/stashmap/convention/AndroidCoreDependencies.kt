package com.jayys.stashmap.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

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
        "junit",
        "kotlinx.coroutines.test"
    )

    // Android test dependencies
    val androidTestLibraries = listOf(
        "androidx.junit",
        "androidx.espresso.core"
    )
}

internal fun Project.applyAndroidCoreDependencies() {
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