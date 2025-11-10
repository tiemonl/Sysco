plugins {
    alias(libs.plugins.garlicbread.android.library.compose)
    alias(libs.plugins.garlicbread.hilt)
}

android {
    namespace = "dev.garlicbread.sysco.sharedmodules.planets.wiring"
}

dependencies {
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi.converter)
    ksp(libs.moshi.kotlin.codegen)

    api(projects.sharedmodules.planets.impl)
}