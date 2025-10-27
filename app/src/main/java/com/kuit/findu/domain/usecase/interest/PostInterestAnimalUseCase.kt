package com.kuit.findu.domain.usecase.interest

import com.kuit.findu.domain.repository.InterestRepository
import javax.inject.Inject

class PostInterestAnimalUseCase @Inject constructor(
    private val interestRepository: InterestRepository
) {
    suspend operator fun invoke(reportId: Long): Result<Unit> {
        return interestRepository.registerInterestAnimal(reportId)
    }
}