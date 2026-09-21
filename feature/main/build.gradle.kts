plugins {
    id("stashmap.android.feature")
}

android {
    namespace = "com.jayys.stashmap.feature.main"
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:stash"))
    implementation(project(":feature:profile"))
}
