plugins {
    id("KotlinJvmPlugin")
}

applyKotlinJvmCoreDependencies()

dependencies {
    implementation(project(":core:model"))
}
