plugins {
    `kotlin-dsl`
}

dependencies {
    // 플러그인 코드 컴파일에만 필요. 런타임 클래스는 루트 build.gradle.kts의 `apply false` 선언이 제공
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "stashmap.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidFeature") {
            id = "stashmap.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "stashmap.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "stashmap.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("jvmLibrary") {
            id = "stashmap.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}
