plugins {
    id("KotlinJvmPlugin")
}

applyKotlinJvmCoreDependencies()

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
}
