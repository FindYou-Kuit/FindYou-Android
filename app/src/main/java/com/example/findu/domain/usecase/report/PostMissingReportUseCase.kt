package com.example.findu.domain.usecase.report

import com.example.findu.domain.model.report.MissingReportData
import com.example.findu.domain.repository.report.ReportRepository
import javax.inject.Inject

class PostMissingReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(missingReportData: MissingReportData): Result<Unit> =
        reportRepository.postMissingReport(missingReportData)
}