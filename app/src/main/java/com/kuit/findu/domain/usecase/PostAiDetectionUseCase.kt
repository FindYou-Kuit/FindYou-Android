package com.kuit.findu.domain.usecase

import com.kuit.findu.domain.model.breed.AiDetectionData
import com.kuit.findu.domain.repository.BreedRepository

class PostAiDetectionUseCase(
    private val breedRepository: BreedRepository
) {
    suspend operator fun invoke(imageUrl: String): Result<AiDetectionData> =
        breedRepository.postAiDetection(imageUrl)
}