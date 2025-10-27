package com.kuit.findu.data.dataremote.service

import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.request.AiDetectionRequestDto
import com.kuit.findu.data.dataremote.model.response.breed.AiDetectionResponseDto
import com.kuit.findu.data.dataremote.model.response.breed.BreedResponseDto
import com.kuit.findu.data.dataremote.model.response.breed.BreedValidationResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BreedService {
    @GET("/api/v2/breeds")
    suspend fun getBreeds(): BaseResponse<BreedResponseDto>

    @GET("/api/v1/breeds/validation")
    suspend fun getBreedValidation(
        @Query("breedName") breedName: String
    ): BaseResponse<BreedValidationResponseDto>

    @POST("/api/v2/breeds/ai-detection")
    suspend fun postAiDetection(
        @Body request: AiDetectionRequestDto
    ): BaseResponse<AiDetectionResponseDto>
}