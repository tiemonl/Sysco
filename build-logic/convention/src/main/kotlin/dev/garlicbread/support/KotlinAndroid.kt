package dev.garlicbread.support

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        compileSdk {
            version = release(AppConfig.compileSdk)
        }

        defaultConfig {
            minSdk = AppConfig.minSdk
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = AppConfig.sourceCompatibility
            targetCompatibility = AppConfig.targetCompatibility
        }
    }

    configureKotlin()
    dependencies {
        add("implementation", libs.findLibrary("kotlinx-immutable").get())
        add("implementation", libs.findLibrary("kotlin-stdlib").get())
    }
}

/**
 * Configure base Kotlin options for JVM (non-Android)
 */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = AppConfig.sourceCompatibility
        targetCompatibility = AppConfig.targetCompatibility
    }

    configureKotlin()
}

/**
 * Configure base Kotlin options
 */
private fun Project.configureKotlin() {
    // Use withType to workaround https://youtrack.jetbrains.com/issue/KT-55947
    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            // Use typed enum value for jvmTarget
            jvmTarget.set(JvmTarget.JVM_21)

            // Set allWarningsAsErrors from project property safely
            val warningsAsErrors: String? by project
            allWarningsAsErrors.set(warningsAsErrors.toBoolean())

            // Use addAll to append opt-in flags, cannot use +-operator
            freeCompilerArgs.addAll(
                listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-opt-in=kotlinx.coroutines.FlowPreview",
                    "-Xannotation-default-target=param-property"
                )
            )
        }
    }
}