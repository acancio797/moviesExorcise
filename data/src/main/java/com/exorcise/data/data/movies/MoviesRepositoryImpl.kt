package com.exorcise.data.data.movies

import com.exorcise.data.local.MoviesLocalDataSource
import com.exorcise.domain.model.ApiConfiguration
import com.exorcise.domain.model.MovieDetails
import com.exorcise.domain.model.MovieSummary
import com.exorcise.domain.model.TypeMovieOrder
import com.exorcise.domain.repository.ConfigurationRepository
import com.exorcise.domain.repository.MoviesRepository
import javax.inject.Inject
import kotlin.math.roundToInt

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val configurationRepository: ConfigurationRepository,
    private val moviesLocalDataSource: MoviesLocalDataSource,
) :
    MoviesRepository {
    override suspend fun fetchPopularMovies(
        page: Int,
        type: TypeMovieOrder
    ): Result<List<MovieSummary>> {
        return fetchWithConfiguration { apiConfiguration ->
            val data = moviesRemoteDataSource.getPopularMovies(page, type).fold(
                onSuccess = { popularMoviesResponse ->
                    var data = popularMoviesResponse.popularMovies.map { movie ->
                        MovieSummary(
                            id = movie.id,
                            title = movie.title,
                            rating = movie.voteAverage,
                            releaseDate = movie.releaseDate,
                            imageUrl = apiConfiguration.urlForBackdrop(movie.backdropPath)
                        )
                    }
                    moviesLocalDataSource.incertMovies(data)
                    return@fold Result.success(data)
                },
                onFailure = { error ->
                    return@fetchWithConfiguration Result.failure(error)
                }
            )
            Result.success(data!!.getOrElse { error ->
                return@fetchWithConfiguration Result.failure(error)
            })
        }
    }

    override suspend fun fetchPopularTv(page: Int): Result<List<MovieSummary>> {
        return fetchWithConfiguration { apiConfiguration ->
            Result.success(
                moviesRemoteDataSource.getPopularTv(page).getOrElse { error ->
                    return@fetchWithConfiguration Result.failure(error)
                }.popularMovies.map { movie ->
                    MovieSummary(
                        id = movie.id,
                        title = movie.name,
                        rating = movie.popularity.toFloat(),
                        releaseDate = movie.firstAirDate,
                        imageUrl = apiConfiguration.urlForBackdrop(movie.backdropPath)
                    )
                }
            )
        }
    }

    override suspend fun fetchMovieDetails(id: Int): Result<MovieDetails> {
        return fetchWithConfiguration {
            Result.success(
                moviesRemoteDataSource.getMovieDetails(id).getOrElse { error ->
                    return@fetchWithConfiguration Result.failure(error)
                }.let { details ->
                    MovieDetails(
                        id = details.id,
                        title = details.title,
                        runtime = details.runtime,
                        tagline = details.tagline,
                        overview = details.overview,
                        releaseDate = details.releaseDate,
                        voteAverage = (details.voteAverage * 10.0f).roundToInt() / 10.0f,
                        genres = details.genres.map { genre ->
                            genre.name
                        },
                        backdropUrl = it.urlForBackdrop(details.backdropPath),
                        posterUrl = it.urlForPoster(details.posterPath)
                    )
                }
            )
        }
    }

    override suspend fun fetchLocal(): Result<List<MovieSummary>> {
        var data = moviesLocalDataSource.getGetLocalMovies()
        return Result.success(data)
    }

    private suspend fun <T> fetchWithConfiguration(
        onSuccessConfigurationFetch: suspend (ApiConfiguration) -> Result<T>
    ): Result<T> {
        val apiConfiguration = configurationRepository.fetchConfiguration().getOrElse { error ->
            return Result.failure(error)
        }

        return onSuccessConfigurationFetch(apiConfiguration)
    }

}
