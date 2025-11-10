package dev.garlicbread.convention

import com.android.build.api.dsl.LibraryExtension
import dev.garlicbread.support.AppConfig
import dev.garlicbread.support.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        with(project) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                testOptions.targetSdk = AppConfig.targetSdk
            }
        }
    }
}