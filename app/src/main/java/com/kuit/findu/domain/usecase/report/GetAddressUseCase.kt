package com.kuit.findu.domain.usecase.report

import com.kuit.findu.domain.model.report.AddressData
import com.kuit.findu.domain.repository.report.ReportRepository
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(lat: Double, lng: Double): Result<AddressData> =
        reportRepository.getAddress(lat, lng)

}