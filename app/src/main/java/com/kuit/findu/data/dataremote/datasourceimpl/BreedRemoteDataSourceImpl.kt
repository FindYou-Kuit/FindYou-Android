package com.kuit.findu.data.dataremote.datasourceimpl

import com.kuit.findu.data.dataremote.datasource.BreedRemoteDataSource
import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.request.AiDetectionRequestDto
import com.kuit.findu.data.dataremote.model.response.breed.AiDetectionResponseDto
import com.kuit.findu.data.dataremote.model.response.breed.BreedResponseDto
import com.kuit.findu.data.dataremote.model.response.breed.BreedValidationResponseDto
import com.kuit.findu.data.dataremote.service.BreedService
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