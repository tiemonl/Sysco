package dev.garlicbread.sysco.sharedmodules.planets.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.compose.AndroidFragment
import dev.garlicbread.sysco.sharedmodules.navigation.Navigator
import dev.garlicbread.sysco.sharedmodules.planets.PlanetsEntry
import dev.garlicbread.sysco.sharedmodules.planets.PlanetsNavKey
import dev.garlicbread.sysco.sharedmodules.planets.data.PlanetDetailsData
import dev.garlicbread.sysco.sharedmodules.planets.impl.ui.PlanetDetailFragment
import dev.garlicbread.sysco.sharedmodules.planets.impl.ui.PlanetsListScreen
import javax.inject.Inject

class PlanetsEntryReal @Inject constructor(
    private val navigator: Navigator
) : PlanetsEntry {

    @Composable
    override fun PlanetsFeature() {
        PlanetsListScreen {
            navigator.navigateTo(
                PlanetsNavKey.PlanetDetail(
                    PlanetDetailsData(
                        id = it.id,
                        name = "Planet ${it.name}",
                    )
                )
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun PlanetDetail(planetData: PlanetDetailsData, onBackPress: () -> Unit) {
        Column(Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        text = planetData.name,
                        fontSize = 20.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )
                },
                expandedHeight = TopAppBarDefaults.MediumAppBarCollapsedHeight,
                windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back press",
                        )
                    }
                }
            )
            val bundle = remember(planetData.id) {
                bundleOf(PlanetDetailFragment.BUNDLE_ID to planetData.id)
            }
            AndroidFragment<PlanetDetailFragment>(
                arguments = bundle,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}