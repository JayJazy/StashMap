plugins {
    id("AndroidLibraryPlugin")
}

android {
    namespace = "com.jayys.stashmap.core.common"
}

applyAndroidCoreDependencies()

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
}
