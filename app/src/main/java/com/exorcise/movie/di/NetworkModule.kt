package com.exorcise.movie.di

import com.exorcise.movie.api.MoviesApiClient
import com.exorcise.movie.api.retrofit.TMDbClient
import com.exorcise.movie.api.retrofit.adapters.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): MoviesApiClient {
        return retrofit.create(TMDbClient::class.java)
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, @BaseUrl baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .client(okHttpClient)
            .build()

    @Provides
    fun provideOkHttpClient(@ApiKey apiKey: String) =
        OkHttpClient.Builder().addInterceptor { chain ->
            val originalRequest: Request = chain.request()
            val originalHttpUrl: HttpUrl = originalRequest.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(API_KEY_QUERY_PARAM_NAME, apiKey)
                .build()

            chain.proceed(originalRequest.newBuilder().url(url).build())
        }.addInterceptor(
            HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        ).build()

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl() = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey() ="f0e24a3cd7232a6813ca44acbfca9e69"

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class BaseUrl

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class ApiKey

    companion object {
        const val API_KEY_QUERY_PARAM_NAME = "api_key"
    }
}
