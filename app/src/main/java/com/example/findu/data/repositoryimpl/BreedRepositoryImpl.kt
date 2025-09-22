package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.BreedRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.breed.toDomain
import com.example.findu.data.mapper.torequest.toAiDetectionRequest
import com.example.findu.domain.model.breed.AiDetectionData
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.BreedValidationData
import com.example.findu.domain.repository.BreedRepository
import javax.inject.Inject

class BreedRepositoryImpl @Inject constructor(
    private val breedRemoteDataSource: BreedRemoteDataSource
) : BreedRepository {
    override suspend fun getBreedData(): Result<BreedData> =
        runCatching {
            breedRemoteDataSource.getBreed().handleBaseResponse().getOrThrow().toDomain()
        }


    override suspend fun getBreedValidation(breedName: String): Result<BreedValidationData> =
        runCatching {
            breedRemoteDataSource.getBreedValidation(breedName).handleBaseResponse().getOrThrow()
                .toDomain()
        }

    override suspend fun postAiDetection(imageUrl: String): Result<AiDetectionData> =
        runCatching {
            breedRemoteDataSource.postAiDetection(imageUrl.toAiDetectionRequest()).handleBaseResponse().getOrThrow().toDomain()
        }
}
