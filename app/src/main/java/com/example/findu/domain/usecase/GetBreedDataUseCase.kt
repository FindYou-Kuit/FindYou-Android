package com.example.findu.domain.usecase

import com.example.findu.domain.repository.BreedRepository
import javax.inject.Inject

class GetBreedDataUseCase @Inject constructor(
    private val breedRepository: BreedRepository
) {
    suspend operator fun invoke() = breedRepository.getBreedData()
}