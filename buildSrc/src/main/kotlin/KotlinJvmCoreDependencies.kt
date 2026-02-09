import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Kotlin/JVM Core 모듈에 공통으로 사용되는 라이브러리 목록
 * :core:common, :core:model, :core:domain
 */
private object KotlinJvmCoreLibraries {
    // Implementation dependencies
    val libraries = listOf(
        "javax.inject"
    )

    // Test dependencies
    val testLibraries = listOf(
        "junit"
    )
}

/**
 * Kotlin/JVM Core 모듈에 공통 dependencies 적용
 * :core:common, :core:model, :core:domain
 */
fun Project.applyKotlinJvmCoreDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
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