package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.BreedRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.breed.toDomain
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.repository.BreedRepository
import javax.inject.Inject

class BreedRepositoryImpl @Inject constructor(
    private val breedRemoteDataSource: BreedRemoteDataSource
) : BreedRepository {
    override suspend fun getBreedData(): Result<BreedData> =
        runCatching {
            breedRemoteDataSource.getBreed().handleBaseResponse().getOrThrow().toDomain()
        }

}