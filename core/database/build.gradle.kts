plugins {
    id("AndroidLibraryPlugin")
}

android {
    namespace = "com.jayys.stashmap.core.database"
}

applyAndroidCoreDependencies()

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
}