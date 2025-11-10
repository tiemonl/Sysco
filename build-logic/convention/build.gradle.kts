plugins {
  `kotlin-dsl`
}

group = "dev.garlicbread.buildlogic"

java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
  jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_21.toString()))
  }
}

dependencies {
  implementation(libs.kotlin.gradle.plugin)
  implementation(libs.android.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("android-application-convention") {
            id = libs.plugins.garlicbread.android.application.asProvider().get().pluginId
            implementationClass = "dev.garlicbread.convention.AndroidApplicationConventionPlugin"
        }
        register("android-application-compose-convention") {
            id = libs.plugins.garlicbread.android.application.compose.get().pluginId
            implementationClass = "dev.garlicbread.convention.AndroidApplicationComposeConventionPlugin"
        }
        register("android-library-convention") {
            id = libs.plugins.garlicbread.android.library.asProvider().get().pluginId
            implementationClass = "dev.garlicbread.convention.AndroidLibraryConventionPlugin"
        }
        register("android-library-compose-convention") {
            id = libs.plugins.garlicbread.android.library.compose.get().pluginId
            implementationClass = "dev.garlicbread.convention.AndroidLibraryComposeConventionPlugin"
        }
        register("hilt") {
            id = libs.plugins.garlicbread.hilt.get().pluginId
            implementationClass = "dev.garlicbread.convention.HiltConventionPlugin"
        }
    }
}

