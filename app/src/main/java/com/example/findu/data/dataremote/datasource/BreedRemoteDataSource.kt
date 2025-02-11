package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.breed.BreedResponseDto
import com.example.findu.data.dataremote.model.response.breed.BreedValidationResponseDto


interface BreedRemoteDataSource {
    suspend fun getBreed(): BaseResponse<List<BreedResponseDto>>

}