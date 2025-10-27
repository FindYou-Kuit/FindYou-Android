package com.kuit.findu.domain.usecase

import com.kuit.findu.domain.repository.BreedRepository
import javax.inject.Inject

class GetBreedDataUseCase @Inject constructor(
    private val breedRepository: BreedRepository
) {
    suspend operator fun invoke() = breedRepository.getBreedData()
}