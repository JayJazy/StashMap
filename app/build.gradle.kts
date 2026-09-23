plugins {
    id("stashmap.android.application")
}

android {
    namespace = "com.jayys.stashmap"
}

dependencies {
    implementation(project(":feature:main"))
    implementation(project(":core:data"))

    // App specific dependencies
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
}