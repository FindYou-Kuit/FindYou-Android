package com.example.findu.domain.usecase.my

import com.example.findu.domain.repository.MyRepository
import javax.inject.Inject

class GetInterestUseCase @Inject constructor(
    private val myRepository: MyRepository
) {
    suspend operator fun invoke(
        lastReportId: Long,
        lastProtectId: Long,
    ) = myRepository.getMyInterest(
        lastReportId = lastReportId,
        lastProtectId = lastProtectId
    )
}