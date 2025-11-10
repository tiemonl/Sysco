package dev.garlicbread.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import dev.garlicbread.support.AppConfig
import dev.garlicbread.support.configureAndroidCompose
import dev.garlicbread.support.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        with(project) {
            with(pluginManager) {
                apply("dev.garlicbread.convention.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<ApplicationExtension> {
                configureAndroidCompose(this)
            }
        }
    }
}