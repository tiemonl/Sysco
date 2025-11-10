plugins {
    alias(libs.plugins.garlicbread.android.library.compose)
    alias(libs.plugins.garlicbread.hilt)
    alias(libs.plugins.mannodermaus.junit5)
}

android {
    namespace = "com.garlicbread.sysco.sharedmodules.planets.impl"
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.fragment.compose)
    implementation(libs.androidx.compose.material.icons)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    implementation(libs.moshi)
    implementation(libs.moshi.kotlin.codegen)
    implementation(libs.moshi.kotlin)
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi.converter)

    implementation(libs.androidx.hilt.lifecycle.viewmodel.compose)
    implementation(libs.coil)
    implementation(libs.coil.network)
    implementation(libs.glide)
    implementation(libs.kotlinx.immutable)
    implementation(libs.androidx.constraint.layout)

    testImplementation(platform(libs.junit5.bom))
    testImplementation(libs.coroutine.test)
    testImplementation(libs.junit5.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.android)
    testImplementation(libs.truth)
    androidTestImplementation(libs.truth)
    testImplementation(libs.turbine)
    testRuntimeOnly(libs.junit5.engine)

    api(projects.sharedmodules.dispatchers)
    api(projects.sharedmodules.planets.public)
    api(projects.sharedmodules.navigation.public)
}