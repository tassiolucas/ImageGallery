// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version  "7.4.1" apply false
    id("com.android.library") version "7.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("org.jetbrains.dokka") version "1.5.31" apply false
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.5.31")
    }
}