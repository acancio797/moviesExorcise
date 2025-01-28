package com.exorcise.movie.data.configuration

import com.exorcise.data.api.responses.ConfigurationResponse
import com.exorcise.data.api.responses.Images
import com.exorcise.data.data.configuration.ConfigurationRemoteDataSource
import com.exorcise.data.data.configuration.ConfigurationRepositoryImpl
import com.exorcise.domain.model.ApiConfiguration
import com.exorcise.domain.repository.ConfigurationRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ConfigurationRepositoryImplTest {

    private val configurationRemoteDataSource = mockk<ConfigurationRemoteDataSource>()

    private val repository: ConfigurationRepository = ConfigurationRepositoryImpl(configurationRemoteDataSource)


    @Test
    fun fetchConfiguration() = runTest {
        val apiConfigurationResponse = ConfigurationResponse(
            changeKeys = emptyList(),
            images = Images(
                baseUrl = "http://image.org/",
                secureBaseUrl = "https://image.org/",
                backdropSizes = listOf(
                    "w300",
                    "w780",
                    "w1280",
                    "original"
                ),
                posterSizes = listOf(
                    "w342",
                    "w500",
                    "w780",
                    "original"
                ),
                logoSizes = emptyList(),
                profileSizes = emptyList(),
                stillSizes = emptyList()
            )
        )

        val expected = Result.success(
            ApiConfiguration(
                imagesBaseUrl = apiConfigurationResponse.images.secureBaseUrl,
                posterSize = apiConfigurationResponse.images.posterSizes[2],
                backdropSize = apiConfigurationResponse.images.backdropSizes[1]
            )
        )

        val mockConfigurationRemoteDataSource = mockk<ConfigurationRemoteDataSource> {
            coEvery { getApiConfiguration() } returns Result.success(
                apiConfigurationResponse
            )
        }

        val configurationRepository =
            ConfigurationRepositoryImpl(
                mockConfigurationRemoteDataSource
            )

        assertEquals(expected, configurationRepository.fetchConfiguration())
    }

    @Test
    fun fetchConfigurationShouldReturnFailureWhenRemoteCallFails() = runTest {
        // Arrange
        val errorMessage = "Failed to fetch configuration"
        coEvery { configurationRemoteDataSource.getApiConfiguration() } returns Result.failure(Throwable(errorMessage))

        // Act
        val result = repository.fetchConfiguration()

        // Assert
        assertNotNull("Result should not be null", result)
        assertTrue("Result should be failure", result.isFailure)
        assertEquals(errorMessage, result.exceptionOrNull()?.message)
    }

    @Test
    fun fetchConfigurationShouldReturnRemoteConfigurationWhenNoCacheExists() = runTest {
        // Arrange
        val configurationResponse = ConfigurationResponse(
            changeKeys = listOf("key1", "key2"),
            images = Images(
                backdropSizes = listOf("w300", "w780", "w1280"),
                baseUrl = "http://example.com/",
                logoSizes = listOf("w45", "w92"),
                posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780"),
                profileSizes = listOf("w45", "w185", "h632"),
                secureBaseUrl = "https://secure.example.com/",
                stillSizes = listOf("w92", "w185", "w300")
            )
        )

        coEvery { configurationRemoteDataSource.getApiConfiguration() } returns Result.success(configurationResponse)

        // Act
        val result = repository.fetchConfiguration()

        // Assert
        assertNotNull("Result should not be null", result)
        assertTrue("Result should be success", result.isSuccess)

        val apiConfiguration = result.getOrNull()
        assertNotNull("ApiConfiguration should not be null", apiConfiguration)
        assertEquals("https://secure.example.com/", apiConfiguration?.imagesBaseUrl)
        assertEquals("w500", apiConfiguration?.posterSize)
        assertEquals("w300", apiConfiguration?.backdropSize)
    }
}

