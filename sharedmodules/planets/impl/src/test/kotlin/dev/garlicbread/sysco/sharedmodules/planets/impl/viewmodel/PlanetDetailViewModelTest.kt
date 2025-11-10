package dev.garlicbread.sysco.sharedmodules.planets.impl.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsPlanetResponseState
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsPlanetsResponseState
import dev.garlicbread.sysco.sharedmodules.planets.impl.data.Planet
import dev.garlicbread.sysco.sharedmodules.planets.impl.repo.StarWarsPlanetsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PlanetDetailViewModelTest {
    private val mockRepository: StarWarsPlanetsRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: PlanetDetailViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PlanetDetailViewModel(
            repository = mockRepository,
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() {
        assertThat(viewModel.planet.value).isEqualTo(PlanetDetailViewModel.PlanetState.Loading)
    }

    @Test
    fun `getPlanet should emit Loading and then Success state when repository returns successful response`() = runTest {
        val testPlanet = mockk<Planet>()
        val planetId = 1000
        coEvery { mockRepository.getPlanet(planetId) } returns StarWarsPlanetResponseState.Success(testPlanet)

        viewModel.planet.test {
            assertThat(awaitItem()).isEqualTo(PlanetDetailViewModel.PlanetState.Loading)

            viewModel.getPlanet(planetId)

            val successState = awaitItem()
            assertThat(successState).isInstanceOf(PlanetDetailViewModel.PlanetState.Success::class.java)

            val successData = successState as PlanetDetailViewModel.PlanetState.Success
            assertThat(successData.planet).isEqualTo(testPlanet)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `verify id passed into getPlanet is passed into repository`() = runTest {
        val testPlanet = mockk<Planet>()
        val planetId = 1000
        coEvery { mockRepository.getPlanet(planetId) } returns StarWarsPlanetResponseState.Success(testPlanet)

        viewModel.planet.test {
            assertThat(awaitItem()).isEqualTo(PlanetDetailViewModel.PlanetState.Loading)

            viewModel.getPlanet(planetId)

            awaitItem()

            coVerify(exactly = 1) { mockRepository.getPlanet(planetId) }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getPlanets should emit Loading and then Error state when repository returns failing response`() = runTest {
        val errorMessage = "Error message"
        val planetId = 1000
        coEvery { mockRepository.getPlanet(planetId) } returns StarWarsPlanetResponseState.Error(errorMessage)

        viewModel.planet.test {
            assertThat(awaitItem()).isEqualTo(PlanetDetailViewModel.PlanetState.Loading)
            viewModel.getPlanet(planetId)

            val errorState = awaitItem()
            assertThat(errorState).isInstanceOf(PlanetDetailViewModel.PlanetState.Error::class.java)

            val errorData = errorState as PlanetDetailViewModel.PlanetState.Error
            assertThat(errorData.message).isEqualTo(errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

}