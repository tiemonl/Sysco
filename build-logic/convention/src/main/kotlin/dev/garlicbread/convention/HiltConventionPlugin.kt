package dev.garlicbread.convention

import dev.garlicbread.support.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                "implementation"(libs.findLibrary("dagger.hilt").get())
                "ksp"(libs.findLibrary("dagger.hilt.compiler").get())
            }
        }
    }
}