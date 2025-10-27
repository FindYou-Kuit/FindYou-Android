package com.kuit.findu.domain.usecase.my

import com.kuit.findu.domain.repository.MyRepository
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