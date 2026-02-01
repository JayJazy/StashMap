plugins {
    id("KotlinJvmPlugin")
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))

    testImplementation(libs.junit)
}
