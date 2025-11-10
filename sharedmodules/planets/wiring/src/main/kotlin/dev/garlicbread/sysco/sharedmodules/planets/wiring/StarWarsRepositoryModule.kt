package dev.garlicbread.sysco.sharedmodules.planets.wiring

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.garlicbread.sysco.sharedmodules.planets.impl.repo.StarWarsPlanetsRepository
import dev.garlicbread.sysco.sharedmodules.planets.impl.repo.StarWarsPlanetsRepositoryReal

@Module
@InstallIn(ViewModelComponent::class)
interface StarWarsRepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindStarWarsRepository(starWarsRepositoryReal: StarWarsPlanetsRepositoryReal): StarWarsPlanetsRepository
}