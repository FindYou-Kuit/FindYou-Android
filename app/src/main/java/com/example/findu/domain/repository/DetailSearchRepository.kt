package com.example.findu.domain.repository

import com.example.findu.domain.model.search.DetailProtectData
import com.example.findu.domain.model.search.DetailReportData

interface DetailSearchRepository {
    suspend fun getDetailSearchProtect(protectingReportId: Long): Result<DetailProtectData>
    suspend fun getDetailSearchReport(reportId: Long): Result<DetailReportData>
}
