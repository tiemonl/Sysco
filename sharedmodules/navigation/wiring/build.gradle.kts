plugins {
    alias(libs.plugins.garlicbread.android.library)
    alias(libs.plugins.garlicbread.hilt)
}

android {
    namespace = "dev.garlicbread.sysco.sharedmodules.navigation.wiring"
}

dependencies {
    api(projects.sharedmodules.navigation.impl)
}