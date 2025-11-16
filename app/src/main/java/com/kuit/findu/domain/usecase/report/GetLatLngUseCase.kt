package com.kuit.findu.domain.usecase.report

import com.kuit.findu.domain.model.report.LatLngData
import com.kuit.findu.domain.repository.report.ReportRepository
import javax.inject.Inject

class GetLatLngUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(address: String): Result<LatLngData> =
        reportRepository.getLatLng(address)
}