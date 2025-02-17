package com.example.findu.domain.repository.report

import android.net.Uri
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.model.report.MissingReportData
import com.example.findu.domain.model.report.WitnessReportData
import okhttp3.MultipartBody

interface ReportRepository {
    suspend fun postImageAnalysis(encodeString: String): Result<GptData>

    suspend fun uploadImages(files: List<MultipartBody.Part>): Result<List<String>>

    suspend fun postMissingReport(missingReportData: MissingReportData): Result<Unit>

    suspend fun postWitnessReport(witnessReportData: WitnessReportData): Result<Unit>
}