package com.example.findu.domain.repository.report

import android.net.Uri
import com.example.findu.domain.model.report.GptData

interface ReportRepository {
    suspend fun postImageAnalysis(imageUri: Uri) : Result<GptData>
}