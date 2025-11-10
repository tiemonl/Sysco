package dev.garlicbread.sysco.sharedmodules.planets.impl.repo

import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsPlanetResponseState
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsPlanetsResponseState

interface StarWarsPlanetsRepository {
    suspend fun getPlanets(): StarWarsPlanetsResponseState
    suspend fun getPlanet(id: Int): StarWarsPlanetResponseState
}