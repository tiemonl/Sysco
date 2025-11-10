package dev.garlicbread.sysco.sharedmodules.planets.impl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsPlanetsResponseState
import dev.garlicbread.sysco.sharedmodules.planets.impl.data.Planet
import dev.garlicbread.sysco.sharedmodules.planets.impl.repo.StarWarsPlanetsRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetsListViewModel @Inject constructor(
    private val repository: StarWarsPlanetsRepository
) : ViewModel() {
    private val _planets = MutableStateFlow<PlanetsState>(PlanetsState.Loading)
    val planets = _planets.asStateFlow()

    fun getPlanets() = viewModelScope.launch {
        when (val response = repository.getPlanets()) {
            is StarWarsPlanetsResponseState.Success -> {
                _planets.value = PlanetsState.Success(response.planets.toImmutableList())
            }

            is StarWarsPlanetsResponseState.Error -> {
                _planets.value = PlanetsState.Error(response.message)
            }
        }
    }

    sealed interface PlanetsState {
        data object Loading : PlanetsState
        data class Success(val planets: ImmutableList<Planet>) : PlanetsState
        data class Error(val message: String) : PlanetsState
    }
}