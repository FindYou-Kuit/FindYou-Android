package com.kuit.findu.domain.usecase.report

import com.kuit.findu.domain.model.report.WitnessReportData
import com.kuit.findu.domain.repository.report.ReportRepository
import javax.inject.Inject

class PostWitnessReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(witnessReportData: WitnessReportData): Result<Unit> =
        reportRepository.postWitnessReport(witnessReportData)
}