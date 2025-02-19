package com.example.findu.domain.usecase.interest

import com.example.findu.domain.repository.InterestRepository
import javax.inject.Inject

class PostInterestReportAnimalUseCase @Inject constructor(
    private val interestRepository: InterestRepository
) {
    suspend operator fun invoke(id: Long) = interestRepository.getInterestReportAnimals(id)
}