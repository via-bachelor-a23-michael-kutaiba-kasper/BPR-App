// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath(`secrets-gradle-plugin`())
        classpath(`build-gradle`())
        classpath(`kotlin-gradle-plugin`())
        classpath("com.google.gms:google-services:4.4.0")
    }
}


plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.42" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}