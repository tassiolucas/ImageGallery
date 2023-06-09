pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url ="https://jitpack.io")
        maven(url = "https://kotlin.bintray.com/kotlinx/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url ="https://jitpack.io")
        maven(url = "https://kotlin.bintray.com/kotlinx/")
    }
}
rootProject.name = "ImageGallery"
include(":app")
