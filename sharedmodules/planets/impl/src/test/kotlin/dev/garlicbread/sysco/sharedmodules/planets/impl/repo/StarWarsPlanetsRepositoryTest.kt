package dev.garlicbread.sysco.sharedmodules.planets.impl.repo

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsApi
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsPlanetResponseState
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsPlanetsResponseState
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarwarsPlanetsContract
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class StarWarsPlanetsRepositoryTest {
    private val starWarsApi: StarWarsApi = mockk()
    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: StarWarsPlanetsRepository

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = StarWarsPlanetsRepositoryReal(starWarsApi, dispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Nested
    inner class Planets {
        @Test
        fun `getPlanets returns Success state when API call is successful`() = runTest {
            val contract = buildGetPlanetsResponse()
            coEvery { starWarsApi.getPlanets() } returns contract

            val result = repository.getPlanets()

            assertThat(result).isInstanceOf(StarWarsPlanetsResponseState.Success::class.java)
            result as StarWarsPlanetsResponseState.Success
            assertThat(result.planets).hasSize(10)
            coVerify(exactly = 1) { starWarsApi.getPlanets() }
        }

        @Test
        fun `getPlanets should return Error state with HttpException message`() = runTest {
            val expectedErrorMessage = "HTTP Exception: 404"
            val body = "Not Found".toResponseBody("text/plain".toMediaType())
            val response = Response.error<Any>(404, body)
            coEvery { starWarsApi.getPlanets() } throws HttpException(response)


            val result = repository.getPlanets()

            assertThat(result).isInstanceOf(StarWarsPlanetsResponseState.Error::class.java)
            result as StarWarsPlanetsResponseState.Error
            assertThat(result.message).contains(expectedErrorMessage)
            coVerify(exactly = 1) { starWarsApi.getPlanets() }

        }

        @Test
        fun `getPlanets should return Error state with Unknown Error message`() = runTest {
            val exceptionMessage = "Boom"
            coEvery { starWarsApi.getPlanets() } throws RuntimeException(exceptionMessage)

            val result = repository.getPlanets()

            assertThat(result).isInstanceOf(StarWarsPlanetsResponseState.Error::class.java)
            val errorState = result as StarWarsPlanetsResponseState.Error
            assertThat(errorState.message).contains(exceptionMessage)
            coVerify { starWarsApi.getPlanets() }
        }

        @Test
        fun `getPlanets should return Error state when Error with IOException message`() = runTest {
            val exceptionMessage = "An unknown error occurred"
            coEvery { starWarsApi.getPlanets() } throws IOException(exceptionMessage)

            val result = repository.getPlanets()

            assertThat(result).isInstanceOf(StarWarsPlanetsResponseState.Error::class.java)
            val errorState = result as StarWarsPlanetsResponseState.Error
            assertThat(errorState.message).contains(exceptionMessage)
            coVerify { starWarsApi.getPlanets() }
        }
    }

    @Nested
    inner class Planet {
        @Test
        fun `getPlanet should return Success state when API call is successful`() = runTest {
            val contract = buildGetPlanetResponse()
            coEvery { starWarsApi.getPlanet(1) } returns contract

            val result = repository.getPlanet(1)

            assertThat(result).isInstanceOf(StarWarsPlanetResponseState.Success::class.java)
            val successState = result as StarWarsPlanetResponseState.Success
            assertThat(successState.planet).isEqualTo(contract.mapToPlanet())
            assertThat(successState.planet.name).isEqualTo("Tatooine")
            coVerify { starWarsApi.getPlanet(1) }
        }

        @Test
        fun `getPlanet returns Error with HttpException message`() = runTest(dispatcher) {
            val body = "Not Found".toResponseBody("text/plain".toMediaType())
            val response = Response.error<Any>(404, body)
            coEvery { starWarsApi.getPlanet(1) } throws HttpException(response)

            val result = repository.getPlanet(1)

            assertThat(result).isInstanceOf(StarWarsPlanetResponseState.Error::class.java)
            val message = (result as StarWarsPlanetResponseState.Error).message
            // Repo formats: "HTTP Exception: {code}, {e.message()}"
            assertThat(message).contains("HTTP Exception: 404")
        }

        @Test
        fun `getPlanet returns Error with IOException message`() = runTest(dispatcher) {
            coEvery { starWarsApi.getPlanet(1) } throws IOException("no internet")

            val result = repository.getPlanet(1)

            assertThat(result).isInstanceOf(StarWarsPlanetResponseState.Error::class.java)
            val message = (result as StarWarsPlanetResponseState.Error).message
            assertThat(message).isEqualTo("Network Error: no internet")
        }

        @Test
        fun `getPlanet returns Error with Unknown Error message`() = runTest(dispatcher) {
            coEvery { starWarsApi.getPlanet(1) } throws IllegalStateException("weird")

            val result = repository.getPlanet(1)

            assertThat(result).isInstanceOf(StarWarsPlanetResponseState.Error::class.java)
            val message = (result as StarWarsPlanetResponseState.Error).message
            assertThat(message).isEqualTo("Unknown Error: weird")
        }
    }

    private fun buildGetPlanetsResponse(): StarwarsPlanetsContract =
        fromJsonResource("planets-200.json")

    private fun buildGetPlanetResponse(): StarwarsPlanetsContract.PlanetContract =
        fromJsonResource("planet-200.json")


    private inline fun <reified T> fromJsonResource(path: String): T {
        val json = readFromFileInUnitTest(path)
        val adapter = moshi.adapter(T::class.java)
        return requireNotNull(adapter.fromJson(json)) {
            "Failed to parse ${T::class.java.simpleName} from $path"
        }
    }

    private fun readFromFileInUnitTest(fileName: String): String = javaClass.classLoader
        ?.getResourceAsStream(fileName)
        ?.bufferedReader()
        .use { it?.readText() }
        ?: error("Test resource not found: $fileName")
}