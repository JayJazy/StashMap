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
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:common"))

    // UI specific dependency
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
}