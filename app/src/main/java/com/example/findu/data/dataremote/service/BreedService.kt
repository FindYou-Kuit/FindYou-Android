package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.breed.BreedResponseDto
import com.example.findu.data.dataremote.model.response.breed.BreedValidationResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BreedService {
    @GET("/api/v1/breeds")
    suspend fun getBreeds(): BaseResponse<List<BreedResponseDto>>

    @GET("/api/v1/breeds/validation")
    suspend fun getBreedValidation(
        @Query("breedName") breedName: String
    ): BaseResponse<BreedValidationResponseDto>
}