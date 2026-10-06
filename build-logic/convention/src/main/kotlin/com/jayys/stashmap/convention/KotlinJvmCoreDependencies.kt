package com.jayys.stashmap.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

private object KotlinJvmCoreLibraries {
    // Api dependencies — Repository 가 Flow/StateFlow 를 공개 시그니처로 노출
    val apiLibraries = listOf(
        "kotlinx.coroutines.core"
    )

    // Implementation dependencies
    val libraries = listOf(
        "javax.inject"
    )

    // Test dependencies
    val testLibraries = listOf(
        "junit",
        "kotlinx.coroutines.test"
    )
}

/**
 * Kotlin/JVM Core 모듈에 공통 dependencies 적용
 * :core:model, :core:domain, :core:testing
 */
internal fun Project.applyKotlinJvmCoreDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        // Api dependencies
        KotlinJvmCoreLibraries.apiLibraries.forEach { libraryKey ->
            add("api", libs.findLibrary(libraryKey).get())
        }

        // Implementation dependencies
        KotlinJvmCoreLibraries.libraries.forEach { libraryKey ->
            add("implementation", libs.findLibrary(libraryKey).get())
        }

        // Test dependencies
        KotlinJvmCoreLibraries.testLibraries.forEach { libraryKey ->
            add("testImplementation", libs.findLibrary(libraryKey).get())
        }
    }
}