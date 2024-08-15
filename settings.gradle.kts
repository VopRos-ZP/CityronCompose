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
        maven(url = "https://artifactory-external.vkpartner.ru/artifactory/maven")
        mavenCentral()
    }
}

rootProject.name = "Cityron"
include(":app")
