package dev.garlicbread.sysco.sharedmodules.navigation.impl

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import dev.garlicbread.sysco.sharedmodules.navigation.Navigator
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class NavigatorReal @Inject constructor() : Navigator {

    private val appBackStack = mutableStateListOf<NavKey>()


    override val backStack: SnapshotStateList<NavKey>
        get() = appBackStack

    override fun navigateTo(destination: NavKey) {
        appBackStack.add(destination)
    }

    override fun mainNavigation(destination: NavKey) {
        appBackStack.clear()
        navigateTo(destination)
    }

    override fun popBackStack() {
        appBackStack.removeLastOrNull()
    }

}