pluginManagement {
    includeBuild("build-logic")
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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Sysco"
include(":app")
include(":sharedmodules:dispatchers")
include(":sharedmodules:navigation:impl")
include(":sharedmodules:navigation:public")
include(":sharedmodules:navigation:wiring")
include(":sharedmodules:planets:impl")
include(":sharedmodules:planets:public")
include(":sharedmodules:planets:wiring")
