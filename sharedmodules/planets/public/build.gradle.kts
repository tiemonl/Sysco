plugins {
    alias(libs.plugins.garlicbread.android.library.compose)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.garlicbread.sysco.sharedmodules.planets"
}

dependencies {
    api(libs.serialization.json)
    api(libs.androidx.navigation3.ui)
    api(libs.androidx.navigation3.runtime)
}