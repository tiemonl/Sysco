plugins {
    alias(libs.plugins.garlicbread.android.library.compose)
    alias(libs.plugins.garlicbread.hilt)
}

android {
    namespace = "com.garlicbread.sysco.sharedmodules.navigation.impl"
}

dependencies {
    api(projects.sharedmodules.navigation.public)
}