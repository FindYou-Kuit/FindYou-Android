package com.example.findu.domain.repository.report

import android.net.Uri
import com.example.findu.domain.model.report.AddressData
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.model.report.LatLngData
import com.example.findu.domain.model.report.MissingReportData
import com.example.findu.domain.model.report.WitnessReportData
import okhttp3.MultipartBody

interface ReportRepository {
    suspend fun postImageAnalysis(
        dogList: List<String>,
        catList: List<String>,
        etcList: List<String>,
        encodeString: String
    ): Result<GptData>

    suspend fun uploadImages(files: List<MultipartBody.Part>): Result<List<String>>

    suspend fun postMissingReport(missingReportData: MissingReportData): Result<Unit>

    suspend fun postWitnessReport(witnessReportData: WitnessReportData): Result<Unit>

    suspend fun getAddress(lat: Double, lng: Double): Result<AddressData>
    suspend fun getLatLng(address: String): Result<LatLngData>

    suspend fun deleteReport(reportId: Long): Result<Unit>
}