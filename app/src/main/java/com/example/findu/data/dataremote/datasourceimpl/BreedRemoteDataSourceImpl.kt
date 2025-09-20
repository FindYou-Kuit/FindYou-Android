package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.BreedRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.request.AiDetectionRequestDto
import com.example.findu.data.dataremote.model.response.breed.AiDetectionResponseDto
import com.example.findu.data.dataremote.model.response.breed.BreedResponseDto
import com.example.findu.data.dataremote.model.response.breed.BreedValidationResponseDto
import com.example.findu.data.dataremote.service.BreedService
import javax.inject.Inject

class BreedRemoteDataSourceImpl @Inject constructor(
    private val service: BreedService
) : BreedRemoteDataSource {
    override suspend fun getBreed(): BaseResponse<BreedResponseDto> =
        service.getBreeds()

    override suspend fun getBreedValidation(breedName: String): BaseResponse<BreedValidationResponseDto> =
        service.getBreedValidation(breedName)

    override suspend fun postAiDetection(request: AiDetectionRequestDto): BaseResponse<AiDetectionResponseDto> =
        service.postAiDetection(request)
}