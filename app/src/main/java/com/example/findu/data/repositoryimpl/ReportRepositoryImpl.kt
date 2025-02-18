package com.example.findu.data.repositoryimpl

import android.util.Log
import com.example.findu.data.dataremote.datasource.GptRemoteDataSource
import com.example.findu.data.dataremote.datasource.NaverRemoteDataSource
import com.example.findu.data.dataremote.datasource.ReportRemoteDataSource
import com.example.findu.data.dataremote.model.request.GptRequestDto
import com.example.findu.data.dataremote.model.request.GptRequestDto.Companion.imageContent
import com.example.findu.data.dataremote.model.request.ImageUrl
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.report.toDomain
import com.example.findu.data.mapper.todomain.toDomain
import com.example.findu.data.mapper.torequest.toRequestDto
import com.example.findu.domain.model.report.AddressData
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.model.report.MissingReportData
import com.example.findu.domain.model.report.WitnessReportData
import com.example.findu.domain.repository.report.ReportRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val gptRemoteDataSource: GptRemoteDataSource,
    private val reportRemoteDataSource: ReportRemoteDataSource,
    private val naverRemoteDataSource: NaverRemoteDataSource
) : ReportRepository {
    override suspend fun postImageAnalysis(encodeString: String): Result<GptData> =
        runCatching {
            val request = GptRequestDto().apply {
                imageContent.imageUrl = ImageUrl(url = encodeString)
            }
            gptRemoteDataSource.postImagePrompt(request).toDomain()
        }

    override suspend fun uploadImages(files: List<MultipartBody.Part>): Result<List<String>> =
        runCatching {
            reportRemoteDataSource.uploadImages(files).handleBaseResponse().getOrThrow()
        }

    override suspend fun postMissingReport(missingReportData: MissingReportData): Result<Unit> =
        runCatching {
            reportRemoteDataSource.postMissingReport(
                missingReportData.toRequestDto()
            ).handleBaseResponse().getOrThrow()
        }

    override suspend fun postWitnessReport(witnessReportData: WitnessReportData): Result<Unit> =
        runCatching {
            reportRemoteDataSource.postWitnessReport(
                witnessReportData.toRequestDto()
            ).handleBaseResponse().getOrThrow()
        }

    override suspend fun getAddress(lat: Double, lng: Double): Result<AddressData> =
        runCatching {
            naverRemoteDataSource.getAddress("$lng,$lat").toDomain()
        }
}