plugins {
    id("AndroidLibraryComposePlugin")
}

android {
    namespace = "com.jayys.stashmap.core.ui"
}

applyAndroidComposeCoreDependencies()

dependencies {
    implementation(project(":core:designsystem"))

    // UI specific dependency
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
}