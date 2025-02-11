package com.example.findu.domain.repository

import com.example.findu.domain.model.breed.BreedData

interface BreedRepository {
    suspend fun getBreedData() : Result<BreedData>

}