package dev.garlicbread.sysco.sharedmodules.planets.impl.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.garlicbread.sysco.sharedmodules.planets.impl.data.Planet

@JsonClass(generateAdapter = true)
data class StarwarsPlanetsContract(
    val count: Int,
    val next: String,
    val previous: Any?,
    val results: List<PlanetContract>
) {
    @JsonClass(generateAdapter = true)
    data class PlanetContract(
        val climate: String,
        val created: String,
        val diameter: String,
        val edited: String,
        val films: List<String>,
        val gravity: String,
        val name: String,
        @Json(name = "orbital_period")
        val orbitalPeriod: String,
        val population: String,
        val residents: List<String>,
        @Json(name = "rotation_period")
        val rotationPeriod: String,
        @Json(name = "surface_water")
        val surfaceWater: String,
        val terrain: String,
        val url: String
    ) {
        fun mapToPlanet(): Planet {
            return Planet(
                climate = climate,
                created = created,
                diameter = diameter,
                edited = edited,
                films = films,
                gravity = gravity,
                name = name,
                orbitalPeriod = orbitalPeriod,
                population = population,
                residents = residents,
                rotationPeriod = rotationPeriod,
                surfaceWater = surfaceWater,
                terrain = terrain,
                url = url,
                id = url.split("/").dropLast(1).last().toInt(),
            )
        }
    }
    fun mapToPlanets(): List<Planet> = results.map { it.mapToPlanet() }
}