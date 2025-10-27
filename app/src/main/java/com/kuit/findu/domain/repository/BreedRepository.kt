package com.kuit.findu.domain.repository

import com.kuit.findu.domain.model.breed.AiDetectionData
import com.kuit.findu.domain.model.breed.BreedData
import com.kuit.findu.domain.model.breed.BreedValidationData

interface BreedRepository {
    suspend fun getBreedData() : Result<BreedData>

    suspend fun getBreedValidation(breedName: String) : Result<BreedValidationData>

    suspend fun postAiDetection(imageUrl: String) : Result<AiDetectionData>
}