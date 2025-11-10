package dev.garlicbread.sysco.sharedmodules.planets.impl.api

import dev.garlicbread.sysco.sharedmodules.planets.impl.data.Planet

sealed class StarWarsPlanetResponseState {
    data class Success(val planet: Planet) : StarWarsPlanetResponseState()
    data class Error(val message: String) : StarWarsPlanetResponseState()
}