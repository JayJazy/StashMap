pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "StashMap"
include(":app")

// Core modules
include(":core:common")
include(":core:model")
include(":core:domain")
include(":core:data")
include(":core:database")
include(":core:ui")
include(":core:designsystem")

// Feature modules
include(":feature:main")
include(":feature:home")
include(":feature:stash")
include(":feature:profile")