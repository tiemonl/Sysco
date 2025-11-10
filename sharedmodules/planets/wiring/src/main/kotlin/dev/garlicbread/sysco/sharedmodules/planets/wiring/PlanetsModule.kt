package dev.garlicbread.sysco.sharedmodules.planets.wiring

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dev.garlicbread.sysco.sharedmodules.planets.PlanetsEntry
import dev.garlicbread.sysco.sharedmodules.planets.impl.PlanetsEntryReal

@Module
@InstallIn(ActivityRetainedComponent::class)
interface PlanetsModule {

    @Binds
    fun bindPlanetsEntry(planetsEntryReal: PlanetsEntryReal): PlanetsEntry
}