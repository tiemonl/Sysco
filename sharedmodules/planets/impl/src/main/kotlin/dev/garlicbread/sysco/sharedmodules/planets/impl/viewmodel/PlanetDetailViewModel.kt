package dev.garlicbread.sysco.sharedmodules.planets.impl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsPlanetResponseState
import dev.garlicbread.sysco.sharedmodules.planets.impl.data.Planet
import dev.garlicbread.sysco.sharedmodules.planets.impl.repo.StarWarsPlanetsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetDetailViewModel @Inject constructor(
    private val repository: StarWarsPlanetsRepository
) : ViewModel() {
    private val _planet = MutableStateFlow<PlanetState>(PlanetState.Loading)
    val planet = _planet.asStateFlow()

    fun getPlanet(id: Int) = viewModelScope.launch {
        when (val response = repository.getPlanet(id)) {
            is StarWarsPlanetResponseState.Success -> {
                _planet.value = PlanetState.Success(response.planet)
            }

            is StarWarsPlanetResponseState.Error -> {
                _planet.value = PlanetState.Error(response.message)
            }
        }
    }

    sealed interface PlanetState {
        data object Loading : PlanetState
        data class Success(val planet: Planet) : PlanetState
        data class Error(val message: String) : PlanetState
    }
}