package dev.garlicbread.sysco.sharedmodules.planets.impl.repo

import dev.garlicbread.sysco.sharedmodules.dispatchers.IoDispatcher
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsApi
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsPlanetResponseState
import dev.garlicbread.sysco.sharedmodules.planets.impl.api.StarWarsPlanetsResponseState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class StarWarsPlanetsRepositoryReal @Inject constructor(
    private val starWarsApi: StarWarsApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : StarWarsPlanetsRepository {
    override suspend fun getPlanets(): StarWarsPlanetsResponseState = withContext(ioDispatcher) {
        return@withContext try {
            val result = starWarsApi.getPlanets()
            StarWarsPlanetsResponseState.Success(result.mapToPlanets())
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is HttpException -> "${HTTP_EXCEPTION_ERROR}${e.code()}, ${e.message()}"
                is IOException -> "$IO_EXCEPTION${e.message}"
                else -> "$UNKNOWN_ERROR${e.message}"
            }
            StarWarsPlanetsResponseState.Error(errorMessage)
        }
    }

    override suspend fun getPlanet(id: Int): StarWarsPlanetResponseState =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = starWarsApi.getPlanet(id)
                StarWarsPlanetResponseState.Success(result.mapToPlanet())
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is HttpException -> "${HTTP_EXCEPTION_ERROR}${e.code()}, ${e.message()}"
                    is IOException -> "$IO_EXCEPTION${e.message}"
                    else -> "$UNKNOWN_ERROR${e.message}"
                }
                StarWarsPlanetResponseState.Error(errorMessage)
            }
        }

    companion object {
        private const val HTTP_EXCEPTION_ERROR = "HTTP Exception: "
        private const val IO_EXCEPTION = "Network Error: "
        private const val UNKNOWN_ERROR = "Unknown Error: "
    }
}