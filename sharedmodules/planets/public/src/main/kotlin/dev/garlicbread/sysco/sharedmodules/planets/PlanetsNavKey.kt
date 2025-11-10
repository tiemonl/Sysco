package dev.garlicbread.sysco.sharedmodules.planets

import androidx.navigation3.runtime.NavKey
import dev.garlicbread.sysco.sharedmodules.planets.data.PlanetDetailsData
import kotlinx.serialization.Serializable

sealed interface PlanetsNavKey : NavKey {
    @Serializable
    data object PlanetsList : PlanetsNavKey

    @Serializable
    data class PlanetDetail(val planetData: PlanetDetailsData) : PlanetsNavKey
}