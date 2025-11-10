package dev.garlicbread.sysco.sharedmodules.planets.impl.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.garlicbread.sysco.sharedmodules.planets.impl.R
import dev.garlicbread.sysco.sharedmodules.planets.data.PlanetDetailsData
import dev.garlicbread.sysco.sharedmodules.planets.impl.data.Planet
import dev.garlicbread.sysco.sharedmodules.planets.impl.ui.PlanetsListScreenTestTags.LIST
import dev.garlicbread.sysco.sharedmodules.planets.impl.ui.common.Error
import dev.garlicbread.sysco.sharedmodules.planets.impl.ui.common.Loading
import dev.garlicbread.sysco.sharedmodules.planets.impl.viewmodel.PlanetsListViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
fun PlanetsListScreen(
    viewModel: PlanetsListViewModel = hiltViewModel(),
    onPlanetClick: (PlanetDetailsData) -> Unit = {},
) {
    val state = viewModel.planets.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.getPlanets()
    }
    PlanetsListScreenImpl(state.value, onPlanetClick)
}

@Composable
fun PlanetsListScreenImpl(
    state: PlanetsListViewModel.PlanetsState,
    onPlanetClick: (PlanetDetailsData) -> Unit
) {
    when (state) {
        is PlanetsListViewModel.PlanetsState.Success -> {
            PlanetsList(planets = state.planets, onPlanetClick = onPlanetClick)
        }

        is PlanetsListViewModel.PlanetsState.Error -> {
            Error(message = state.message)
        }

        is PlanetsListViewModel.PlanetsState.Loading -> {
            Loading()
        }
    }
}

@Composable
fun PlanetsList(planets: ImmutableList<Planet>, onPlanetClick: (PlanetDetailsData) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .testTag(LIST)
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(planets, key = { _, planet -> planet.id }) { index, planet ->
            PlanetCard(
                planet = planet,
                onPlanetClick = onPlanetClick,
                showDivider = index != planets.lastIndex
            )
        }
    }
}

@Composable
fun PlanetCard(
    planet: Planet,
    onPlanetClick: (PlanetDetailsData) -> Unit,
    showDivider: Boolean = true
) {
    Row(
        modifier = Modifier
            .testTag("${PlanetsListScreenTestTags.CARD}_${planet.id}")
            .clickable(onClickLabel = planet.name, role = Role.Button) {
                onPlanetClick(
                    PlanetDetailsData(
                        planet.id,
                        planet.name,
                    )
                )
            }
            .padding(horizontal = 8.dp, vertical = 16.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = "https://picsum.photos/id/${planet.id}/200",
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = planet.name,
        )
        Column(
            modifier = Modifier.padding(start = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = spacedBy(16.dp)
        )
        {
            Text(
                text = planet.name,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(
                text = planet.climate,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
    if (showDivider)
        HorizontalDivider(modifier = Modifier.padding(start = 8.dp))
}

object PlanetsListScreenTestTags {
    const val CARD = "card"
    const val LIST = "list"
}