plugins {
    id("AndroidLibraryComposePlugin")
}

android {
    namespace = "com.jayys.stashmap.feature.main"
}

applyFeatureDependencies()

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:stash"))
    implementation(project(":feature:profile"))
}
