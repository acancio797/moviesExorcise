package com.exorcise.domain.repository


import com.exorcise.domain.model.ApiConfiguration

interface ConfigurationRepository {
    suspend fun fetchConfiguration(): Result<ApiConfiguration>
}
