package com.exorcise.movie.data.configuration

import com.exorcise.movie.model.ApiConfiguration

interface ConfigurationRepository {
    suspend fun fetchConfiguration(): Result<ApiConfiguration>
}
