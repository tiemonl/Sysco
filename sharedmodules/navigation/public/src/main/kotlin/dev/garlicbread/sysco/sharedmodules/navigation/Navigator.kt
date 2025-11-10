package dev.garlicbread.sysco.sharedmodules.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey


typealias EntryProviderInstaller = EntryProviderScope<NavKey>.() -> Unit

interface Navigator {
    /**
     * Represents the navigation back stack, which maintains the sequence of destinations
     * visited and should be used in the {@link dev.garlicbread.sysco.MainActivity} [androidx.navigation3.ui.NavDisplay].
     */
    val backStack: SnapshotStateList<NavKey>

    /**
     * Navigates to the specified destination and adds it to the navigation back stack.
     *
     * @param destination The target [NavKey] destination to navigate to.
     */
    fun navigateTo(destination: NavKey)

    fun mainNavigation(destination: NavKey)

    /**
     * Removes the last entry from the navigation back stack.
     *
     * This method is typically used to navigate back to the previous destination
     * in the stack. If the back stack is empty, this method may have no effect.
     */
    fun popBackStack()
}