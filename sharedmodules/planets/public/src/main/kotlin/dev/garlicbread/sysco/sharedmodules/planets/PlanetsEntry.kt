package dev.garlicbread.sysco.sharedmodules.planets

import androidx.compose.runtime.Composable
import dev.garlicbread.sysco.sharedmodules.planets.data.PlanetDetailsData

interface PlanetsEntry {

    @Composable
    fun PlanetsFeature()

    @Composable
    fun PlanetDetail(
        planetData: PlanetDetailsData,
        onBackPress: () -> Unit = {},
    )

}