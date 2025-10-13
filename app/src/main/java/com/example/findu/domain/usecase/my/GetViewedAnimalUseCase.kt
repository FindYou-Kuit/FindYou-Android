package com.example.findu.domain.usecase.my

import com.example.findu.domain.repository.MyRepository
import javax.inject.Inject

class GetViewedAnimalUseCase @Inject constructor(
    private val myRepository: MyRepository
) {
    suspend operator fun invoke(
        lastId: Long,
    ) = myRepository.getMyViewedAnimals(
        lastId = lastId
    )
}