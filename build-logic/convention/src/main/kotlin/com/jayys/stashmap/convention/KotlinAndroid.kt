package com.jayys.stashmap.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = StashMapConfig.compileSdk

        defaultConfig {
            minSdk = StashMapConfig.minSdk
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildTypes.getByName("release").apply {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
            // minSdk 24 기기에 java.time 이 없다 — dex 단계에서 의존 jar 까지 재작성된다
            isCoreLibraryDesugaringEnabled = true
        }
    }

    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(StashMapConfig.jvmTarget))
        }
    }

    // 위 플래그와 쌍 — 떼어 놓으면 한쪽 누락 시 그 모듈만 조용히 실패한다
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies {
        add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
    }
}

/**
 * Compose 사용 모듈 공통 설정
 */
internal fun configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.buildFeatures.compose = true
}
