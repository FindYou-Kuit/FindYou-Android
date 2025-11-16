package com.kuit.findu.data.dataremote.datasource

import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.request.AiDetectionRequestDto
import com.kuit.findu.data.dataremote.model.response.breed.AiDetectionResponseDto
import com.kuit.findu.data.dataremote.model.response.breed.BreedResponseDto
import com.kuit.findu.data.dataremote.model.response.breed.BreedValidationResponseDto


interface BreedRemoteDataSource {
    suspend fun getBreed(): BaseResponse<BreedResponseDto>

    suspend fun getBreedValidation(breedName: String): BaseResponse<BreedValidationResponseDto>

    suspend fun postAiDetection(request: AiDetectionRequestDto): BaseResponse<AiDetectionResponseDto>
}