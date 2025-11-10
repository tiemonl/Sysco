package dev.garlicbread.sysco.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(val routeId: String) : NavKey {
    @Serializable
    data object Planets : Routes("planets")

    @Serializable
    data class PlanetDetail(val planetId: Int) : Routes("planetDetail/$planetId")
}