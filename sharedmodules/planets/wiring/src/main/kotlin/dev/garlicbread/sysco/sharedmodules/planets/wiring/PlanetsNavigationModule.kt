package dev.garlicbread.sysco.sharedmodules.planets.wiring

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import dev.garlicbread.sysco.sharedmodules.navigation.EntryProviderInstaller
import dev.garlicbread.sysco.sharedmodules.navigation.Navigator
import dev.garlicbread.sysco.sharedmodules.planets.PlanetsEntry
import dev.garlicbread.sysco.sharedmodules.planets.PlanetsNavKey

@Module
@InstallIn(ActivityRetainedComponent::class)
class PlanetsNavigationModule {

    @IntoSet
    @Provides
    fun providePlanetsEntryPointInstaller(
        navigator: Navigator,
        planetsEntry: PlanetsEntry,
    ): EntryProviderInstaller = {
        entry<PlanetsNavKey.PlanetsList> {
            planetsEntry.PlanetsFeature()
        }
        entry<PlanetsNavKey.PlanetDetail> {
            planetsEntry.PlanetDetail(it.planetData) {
                navigator.popBackStack()
            }
        }
    }
}