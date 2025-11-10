package dev.garlicbread.sysco.sharedmodules.planets.impl.util

import dev.garlicbread.sysco.sharedmodules.planets.impl.data.Planet

object PlanetFakes {

    fun createTestPlanet(id: Int = 1) = Planet(
        climate = "test climate",
        created = "test created",
        diameter = "test diameter",
        edited = "test edited",
        films = listOf("test films"),
        gravity = "test gravity",
        id = id,
        name = "test name",
        orbitalPeriod = "test orbitalPeriod",
        population = "test population",
        residents = listOf("test residents 1", "test residents 2"),
        rotationPeriod = "test rotationPeriod",
        surfaceWater = "test surfaceWater",
        terrain = "test terrain",
        url = "test url",
    )
}