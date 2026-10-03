package com.jayys.stashmap.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

private object KotlinJvmCoreLibraries {
    // Api dependencies
    // Repository 인터페이스가 Flow / StateFlow 를 공개 시그니처로 노출하므로
    // 소비자가 별도 선언 없이 해당 타입을 쓸 수 있도록 api 로 전파한다.
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
 * :core:model, :core:domain
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