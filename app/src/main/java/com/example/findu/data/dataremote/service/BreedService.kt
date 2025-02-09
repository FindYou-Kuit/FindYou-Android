package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.response.breed.BreedResponseDto
import retrofit2.http.GET

interface BreedService {
    @GET("/api/v1/reports/breeds")
    suspend fun getBreeds(): BaseResponse<List<BreedResponseDto>>
}