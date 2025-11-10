package dev.garlicbread.sysco.sharedmodules.planets.impl.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import dev.garlicbread.sysco.sharedmodules.planets.impl.ui.PlanetsListScreenTestTags.CARD
import dev.garlicbread.sysco.sharedmodules.planets.impl.ui.PlanetsListScreenTestTags.LIST
import dev.garlicbread.sysco.sharedmodules.planets.impl.ui.common.ErrorTestTag.ERROR
import dev.garlicbread.sysco.sharedmodules.planets.impl.ui.common.LoadingTestTag.LOADING
import dev.garlicbread.sysco.sharedmodules.planets.impl.util.PlanetFakes.createTestPlanet
import dev.garlicbread.sysco.sharedmodules.planets.impl.viewmodel.PlanetsListViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlanetsListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun planetListScreen_displaysCorrectlyWithSuccess() = runTest {
        val planetId1 = 1
        val planetId2 = 2
        composeTestRule.setContent {
            PlanetsListScreenImpl(
                state = PlanetsListViewModel.PlanetsState.Success(
                    listOf(
                        createTestPlanet(planetId1),
                        createTestPlanet(planetId2),
                    ).toImmutableList(),
                ),
                onPlanetClick = { },
            )
        }

        composeTestRule.onNodeWithTag(LIST).assertIsDisplayed()
        composeTestRule.onNodeWithTag("${CARD}_$planetId1").assertExists()
        composeTestRule.onNodeWithTag("${CARD}_$planetId1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("${CARD}_$planetId2").assertExists()
        composeTestRule.onNodeWithTag("${CARD}_$planetId2").assertIsDisplayed()

        composeTestRule.onNodeWithTag(ERROR).assertDoesNotExist()
        composeTestRule.onNodeWithTag(LOADING).assertDoesNotExist()
    }

    @Test
    fun planetListScreen_onClickCallsOnPlanetClick() = runTest {
        val planetId1 = 1
        val planetId2 = 2

        var isClicked = false

        composeTestRule.setContent {
            PlanetsListScreenImpl(
                state = PlanetsListViewModel.PlanetsState.Success(
                    listOf(
                        createTestPlanet(planetId1),
                        createTestPlanet(planetId2),
                    ).toImmutableList(),
                ),
                onPlanetClick = { isClicked = true },
            )
        }

        composeTestRule.onNodeWithTag(LIST).assertIsDisplayed()
        composeTestRule.onNodeWithTag("${CARD}_$planetId1").assertExists()
        composeTestRule.onNodeWithTag("${CARD}_$planetId1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("${CARD}_$planetId2").assertExists()
        composeTestRule.onNodeWithTag("${CARD}_$planetId2").assertIsDisplayed()

        composeTestRule.onNodeWithTag("${CARD}_$planetId1").performClick()

        Truth.assertThat(isClicked).isTrue()

        composeTestRule.onNodeWithTag(ERROR).assertDoesNotExist()
        composeTestRule.onNodeWithTag(LOADING).assertDoesNotExist()
    }

    @Test
    fun planetListScreen_displaysLoading() = runTest {
        composeTestRule.setContent {
            PlanetsListScreenImpl(
                state = PlanetsListViewModel.PlanetsState.Loading
            ) {}
        }

        composeTestRule.onNodeWithTag(LOADING).assertIsDisplayed()

        composeTestRule.onNodeWithTag(LIST).assertDoesNotExist()
        composeTestRule.onNodeWithTag(ERROR).assertDoesNotExist()
    }

    @Test
    fun planetListScreen_displaysError() = runTest {
        composeTestRule.setContent {
            PlanetsListScreenImpl(
                state = PlanetsListViewModel.PlanetsState.Error("error")
            ) {}
        }

        composeTestRule.onNodeWithTag(ERROR).assertIsDisplayed()

        composeTestRule.onNodeWithTag(LOADING).assertDoesNotExist()
        composeTestRule.onNodeWithTag(LIST).assertDoesNotExist()
    }
}