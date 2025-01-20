package com.exorcise.data.data.person

import com.exorcise.domain.model.ApiConfiguration
import com.exorcise.domain.model.PersonDetails
import com.exorcise.domain.repository.ConfigurationRepository
import com.exorcise.domain.repository.PersonRepository
import javax.inject.Inject


class PersonRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: PersonRemoteDataSource,
    private val configurationRepository: ConfigurationRepository,
) : PersonRepository {

    override suspend fun getPopularPerson(): Result<PersonDetails?> {
        return fetchWithConfiguration { apiConfiguration ->
            moviesRemoteDataSource.getPopularPerson(1).fold(
                onSuccess = { popularMoviesResponse ->
                    return@fetchWithConfiguration getDetailPerson(
                        popularMoviesResponse.popularPerson.get(
                            0
                        ).id
                    )
                },
                onFailure = { error ->
                    return@fetchWithConfiguration Result.failure(error)
                }
            )
        }
    }


    suspend fun getDetailPerson(id: Int): Result<PersonDetails> {
        return fetchWithConfiguration { apiConfiguration ->
            Result.success(
                moviesRemoteDataSource.getMostPopular(id).getOrElse { error ->
                    return@fetchWithConfiguration Result.failure(error)
                }.let { details ->
                    PersonDetails(
                        adult = details.adult,
                        alsoKnownAs = details.alsoKnownAs,
                        biography = details.biography,
                        birthday = details.birthday,
                        deathday = details.deathday,
                        gender = details.gender,
                        homepage = details.homepage,
                        id = details.id,
                        imdbId = details.imdbId,
                        knownForDepartment = details.knownForDepartment,
                        name = details.name,
                        profilePath = apiConfiguration.urlForBackdrop(details.profilePath),
                        placeOfBirth = details.placeOfBirth,
                        popularity = details.popularity,
                    )
                }
            )
        }
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