package dev.garlicbread.sysco.sharedmodules.navigation.wiring

import dev.garlicbread.sysco.sharedmodules.navigation.Navigator
import dev.garlicbread.sysco.sharedmodules.navigation.impl.NavigatorReal
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface NavigatorModule {

    @Binds
    fun bindsNavigator(navigatorReal: NavigatorReal): Navigator
}