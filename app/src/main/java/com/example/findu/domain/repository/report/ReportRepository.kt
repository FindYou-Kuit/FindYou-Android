package com.example.findu.domain.repository.report

import com.example.findu.domain.model.report.GptData

interface ReportRepository {
    suspend fun postImageAnalysis() : Result<GptData>
}