// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Libs.kotlin_version}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Libs.kotlin_version}")
        classpath("com.squareup.sqldelight:gradle-plugin:${Libs.SqlDelight.version}")
    }
}

allprojects {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}