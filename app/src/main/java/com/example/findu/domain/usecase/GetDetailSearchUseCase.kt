package com.example.findu.domain.usecase

import com.example.findu.domain.model.search.DetailProtectData
import com.example.findu.domain.model.search.DetailReportData
import com.example.findu.domain.repository.DetailSearchRepository

class GetDetailSearchUseCase(
    private val detailSearchRepository: DetailSearchRepository
) {
    suspend fun getReportData(reportId: Long): Result<DetailReportData> =
        detailSearchRepository.getDetailSearchReport(reportId)

    suspend fun getProtectData(protectingReportId: Long): Result<DetailProtectData> =
        detailSearchRepository.getDetailSearchProtect(protectingReportId)
}