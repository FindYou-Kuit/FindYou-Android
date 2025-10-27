package com.kuit.findu.domain.usecase

import com.kuit.findu.domain.repository.BreedRepository
import javax.inject.Inject

class GetBreedValidationUseCase @Inject constructor(
    private val breedRepository: BreedRepository
) {
    suspend operator fun invoke(breedName: String) = breedRepository.getBreedValidation(breedName)
}