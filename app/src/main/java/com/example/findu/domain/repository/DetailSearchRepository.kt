package com.example.findu.domain.repository

import com.example.findu.domain.model.search.DetailProtectData

interface DetailSearchRepository {
    suspend fun getDetailSearchProtect(protectingReportId: Long): Result<DetailProtectData>
    suspend fun getDetailSearchReport(reportId: Long): Result<DetailProtectData>
}
