plugins {
    id("AndroidAppPlugin")
}

android {
    namespace = "com.jayys.stashmap"
}

applyFeatureDependencies()

dependencies {
    // Feature modules
    implementation(project(":feature:main"))
    implementation(project(":feature:home"))
    implementation(project(":feature:stash"))
    implementation(project(":feature:profile"))

    // App specific dependencies
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
}