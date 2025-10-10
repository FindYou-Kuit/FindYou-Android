package com.example.findu.domain.usecase

import com.example.findu.domain.model.breed.AiDetectionData
import com.example.findu.domain.repository.BreedRepository

class PostAiDetectionUseCase(
    private val breedRepository: BreedRepository
) {
    suspend operator fun invoke(imageUrl: String): Result<AiDetectionData> =
        breedRepository.postAiDetection(imageUrl)
}