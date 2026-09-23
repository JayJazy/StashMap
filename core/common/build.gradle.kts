plugins {
    id("stashmap.android.library")
}

android {
    namespace = "com.jayys.stashmap.core.common"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
}
