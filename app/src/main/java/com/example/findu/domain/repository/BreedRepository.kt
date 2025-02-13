package com.example.findu.domain.repository

import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.BreedValidationData

interface BreedRepository {
    suspend fun getBreedData() : Result<BreedData>

    suspend fun getBreedValidation(breedName: String) : Result<BreedValidationData>
}