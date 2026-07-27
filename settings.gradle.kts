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

rootProject.name = "Kids Demo"
include(":app")
include(":core:model")
include(":core:common")
include(":core:designsystem")
include(":core:ui")
include(":core:datastore")
include(":core:network")
include(":core:database")
include(":core:data")
include(":feature:auth")
include(":feature:splash")
include(":feature:home")
include(":feature:library")
include(":feature:review")
include(":feature:dictionary")
include(":feature:parentslock")
include(":feature:settings")
