package dev.garlicbread.sysco.sharedmodules.planets.wiring

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
object StarWarsApiModule {

    @Provides
    @ViewModelScoped
    fun provideStarWarsApi(): StarWarsApi =
        Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()
                )
            )
            .build()
            .create()
}