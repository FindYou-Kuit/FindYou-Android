package com.kuit.findu.domain.usecase.my

import com.kuit.findu.domain.repository.MyRepository
import javax.inject.Inject

class GetInterestUseCase @Inject constructor(
    private val myRepository: MyRepository,
) {
    suspend operator fun invoke(lastId: Long) =
        myRepository.getMyInterest(lastId)
}