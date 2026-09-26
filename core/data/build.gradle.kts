plugins {
    id("stashmap.android.library")
}

android {
    namespace = "com.jayys.stashmap.core.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:database"))
}