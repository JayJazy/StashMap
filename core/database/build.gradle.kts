plugins {
    id("stashmap.android.library")
}

android {
    namespace = "com.jayys.stashmap.core.database"
}

dependencies {
    implementation(project(":core:model"))
}