package com.example.findu.domain.usecase.report

import com.example.findu.domain.model.report.WitnessReportData
import com.example.findu.domain.repository.report.ReportRepository
import javax.inject.Inject

class PostWitnessReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(witnessReportData: WitnessReportData): Result<Unit> =
        reportRepository.postWitnessReport(witnessReportData)
}