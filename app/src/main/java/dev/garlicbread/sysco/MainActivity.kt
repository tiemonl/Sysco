package dev.garlicbread.sysco

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.AndroidEntryPoint
import dev.garlicbread.sysco.sharedmodules.navigation.EntryProviderInstaller
import dev.garlicbread.sysco.sharedmodules.navigation.Navigator
import dev.garlicbread.sysco.sharedmodules.planets.PlanetsNavKey
import dev.garlicbread.sysco.ui.theme.SyscoTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var entryProviderScopes: Set<@JvmSuppressWildcards EntryProviderInstaller>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.mainNavigation(PlanetsNavKey.PlanetsList)
        enableEdgeToEdge()
        setContent {
            SyscoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.safeDrawing
                ) { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        NavDisplay(
                            entryDecorators = listOf(
                                rememberSaveableStateHolderNavEntryDecorator(),
                                rememberViewModelStoreNavEntryDecorator(),
                            ),
                            backStack = navigator.backStack,
                            onBack = { navigator.popBackStack() },
                            entryProvider = entryProvider {
                                entryProviderScopes.forEach { builder -> this.builder() }
                            }
                        )
                    }
                }
            }
        }
    }
}