package com.example.findu.domain.usecase.report

import com.example.findu.domain.model.report.LatLngData
import com.example.findu.domain.repository.report.ReportRepository
import javax.inject.Inject

class GetLatLngUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(address: String): Result<LatLngData> =
        reportRepository.getLatLng(address)
}