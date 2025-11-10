package dev.garlicbread.support

import org.gradle.api.JavaVersion

object AppConfig {
    const val compileSdk = 36
    const val minSdk = 28
    const val targetSdk = 36

    val sourceCompatibility = JavaVersion.VERSION_21
    val targetCompatibility = JavaVersion.VERSION_21
}