plugins {
    id("stashmap.android.library.compose")
}

android {
    namespace = "com.jayys.stashmap.core.ui"
}

dependencies {
    implementation(project(":core:designsystem"))

    // UI specific dependency
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
}