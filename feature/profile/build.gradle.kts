plugins {
    id("stashmap.android.feature")
}

android {
    namespace = "com.jayys.stashmap.feature.profile"
}

dependencies {
    testImplementation(testFixtures(project(":core:domain")))
}