plugins {
    id("AndroidLibraryComposePlugin")
}

android {
    namespace = "com.jayys.stashmap.core.ui"
}

applyAndroidComposeCoreDependencies()

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))

    // UI specific dependency
    implementation(libs.androidx.lifecycle.runtime.ktx)
}