package dev.garlicbread.sysco.sharedmodules.planets.impl.api

import retrofit2.http.GET
import retrofit2.http.Path

interface StarWarsApi {
    @GET("planets")
    suspend fun getPlanets(): StarwarsPlanetsContract

    @GET("planets/{id}/")
    suspend fun getPlanet(@Path("id") id: Int): StarwarsPlanetsContract.PlanetContract
}