package dev.garlicbread.sysco.sharedmodules.planets.impl.api

import dev.garlicbread.sysco.sharedmodules.planets.impl.data.Planet

sealed class StarWarsPlanetsResponseState {
    data class Success(val planets: List<Planet>) : StarWarsPlanetsResponseState()
    data class Error(val message: String) : StarWarsPlanetsResponseState()
}