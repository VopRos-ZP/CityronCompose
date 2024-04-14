pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven(url = "https://jitpack.io")
        mavenCentral()
    }
}

rootProject.name = "Cityron"
include(":app")
/** Features **/
include(":core:domain")
include(":core:data")

include(":m3:domain")
include(":m3:data")

include(":atlas:domain")
include(":atlas:data")