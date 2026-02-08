plugins {
    id("AndroidAppPlugin")
}

android {
    namespace = "com.jayys.stashmap"
}

applyFeatureDependencies()

dependencies {
    implementation(project(":feature:main"))
    implementation(project(":core:data"))

    // App specific dependencies
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
}