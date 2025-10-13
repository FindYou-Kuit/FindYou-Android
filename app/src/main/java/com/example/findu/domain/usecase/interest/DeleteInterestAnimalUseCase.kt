package com.example.findu.domain.usecase.interest

import com.example.findu.domain.repository.InterestRepository
import javax.inject.Inject

class DeleteInterestAnimalUseCase @Inject constructor(
    private val interestRepository: InterestRepository
) {
    suspend operator fun invoke(reportId: Long): Result<Unit> {
        return interestRepository.deleteInterestAnimal(reportId)
    }
}