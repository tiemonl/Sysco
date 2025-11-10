import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.garlicbread.android.application.compose)
    alias(libs.plugins.garlicbread.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.garlicbread.sysco"

    defaultConfig {
        applicationId = "dev.garlicbread.sysco"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.kotlin.stdlib)

    implementation(libs.androidx.hilt.lifecycle.viewmodel.compose)

    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.navigation3.runtime)

    // public
    implementation(projects.sharedmodules.planets.public)
    implementation(projects.sharedmodules.navigation.public)

    // wiring for dagger graph
    implementation(projects.sharedmodules.planets.wiring)
    implementation(projects.sharedmodules.navigation.wiring)
}