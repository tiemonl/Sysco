package dev.garlicbread.sysco.sharedmodules.planets.data

import kotlinx.serialization.Serializable

@Serializable
data class PlanetDetailsData(
    val id: Int,
    val name: String,
)