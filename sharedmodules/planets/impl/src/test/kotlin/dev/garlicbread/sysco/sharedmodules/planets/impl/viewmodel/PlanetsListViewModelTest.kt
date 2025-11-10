package dev.garlicbread.sysco.sharedmodules.planets.impl.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsPlanetsResponseState
import dev.garlicbread.sysco.sharedmodules.planets.impl.data.Planet
import dev.garlicbread.sysco.sharedmodules.planets.impl.repo.StarWarsPlanetsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PlanetsListViewModelTest {
    private val mockRepository: StarWarsPlanetsRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: PlanetsListViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PlanetsListViewModel(
            repository = mockRepository,
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() {
        assertThat(viewModel.planets.value).isEqualTo(PlanetsListViewModel.PlanetsState.Loading)
    }

    @Test
    fun `getPlanets should emit Loading and then Success state when repository returns successful response`() = runTest {
        val testPlanets = listOf(
            mockk<Planet>(),
            mockk<Planet>(),
        )
        coEvery { mockRepository.getPlanets() } returns StarWarsPlanetsResponseState.Success(testPlanets)

        viewModel.planets.test {
            assertThat(awaitItem()).isEqualTo(PlanetsListViewModel.PlanetsState.Loading)
            viewModel.getPlanets()

            val successState = awaitItem() as PlanetsListViewModel.PlanetsState.Success
            assertThat(successState.planets).isEqualTo(testPlanets)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getPlanets should emit Loading and then Error state when repository returns failing response`() = runTest {
        val errorMessage = "Error message"
        coEvery { mockRepository.getPlanets() } returns StarWarsPlanetsResponseState.Error(errorMessage)

        viewModel.planets.test {
            assertThat(awaitItem()).isEqualTo(PlanetsListViewModel.PlanetsState.Loading)
            viewModel.getPlanets()

            val errorState = awaitItem() as PlanetsListViewModel.PlanetsState.Error
            assertThat(errorState.message).isEqualTo(errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `multiple calls to getPlanets update state correctly`() = runTest {
        val testPlanets1 = listOf(
            mockk<Planet>(),
            mockk<Planet>(),
        )
        val testPlanets2 = listOf(
            mockk<Planet>(),
            mockk<Planet>(),
            mockk<Planet>(),
        )
        coEvery { mockRepository.getPlanets() } returnsMany listOf(
            StarWarsPlanetsResponseState.Success(testPlanets1),
            StarWarsPlanetsResponseState.Success(testPlanets2)
        )

        viewModel.planets.test {
            assertThat(awaitItem()).isEqualTo(PlanetsListViewModel.PlanetsState.Loading)

            viewModel.getPlanets()
            val successState1 = awaitItem() as PlanetsListViewModel.PlanetsState.Success
            assertThat(successState1.planets).isEqualTo(testPlanets1)

            viewModel.getPlanets()
            val successState2 = awaitItem() as PlanetsListViewModel.PlanetsState.Success
            assertThat(successState2.planets).isEqualTo(testPlanets2)

            cancelAndIgnoreRemainingEvents()
        }
    }

}