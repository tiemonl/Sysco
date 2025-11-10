plugins {
    alias(libs.plugins.garlicbread.android.library.compose)
}

android {
    namespace = "com.garlicbread.sysco.sharedmodules.navigation"
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.navigation3.runtime)
    api(libs.androidx.navigation3.ui)
}