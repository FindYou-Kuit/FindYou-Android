package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.GptRemoteDataSource
import com.example.findu.data.dataremote.datasource.NaverRemoteDataSource
import com.example.findu.data.dataremote.datasource.ReportRemoteDataSource
import com.example.findu.data.dataremote.model.request.GptRequestConstants.getPromptText
import com.example.findu.data.dataremote.model.request.GptRequestDto
import com.example.findu.data.dataremote.model.request.GptRequestDto.Companion.imageContent
import com.example.findu.data.dataremote.model.request.GptRequestDto.Companion.textContent
import com.example.findu.data.dataremote.model.request.ImageUrl
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.report.toDomain
import com.example.findu.data.mapper.todomain.report.toList
import com.example.findu.data.mapper.todomain.toDomain
import com.example.findu.data.mapper.torequest.toRequestDto
import com.example.findu.domain.model.report.AddressData
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.model.report.LatLngData
import com.example.findu.domain.model.report.MissingReportData
import com.example.findu.domain.model.report.WitnessReportData
import com.example.findu.domain.repository.report.ReportRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val gptRemoteDataSource: GptRemoteDataSource,
    private val reportRemoteDataSource: ReportRemoteDataSource,
    private val naverRemoteDataSource: NaverRemoteDataSource,
) : ReportRepository {
    override suspend fun postImageAnalysis(
        dogList: List<String>,
        catList: List<String>,
        etcList: List<String>,
        encodeString: String,
    ): Result<GptData> =
        runCatching {
            val request = GptRequestDto().apply {
                imageContent.imageUrl = ImageUrl(encodeString)
                textContent.text = getPromptText(
                    dogList = dogList,
                    catList = catList,
                    etcList = etcList
                )
            }
            gptRemoteDataSource.postImagePrompt(request).toDomain()
        }

    override suspend fun uploadImages(files: List<MultipartBody.Part>): Result<List<String>> =
        runCatching {
            reportRemoteDataSource.uploadImages(files).handleBaseResponse().getOrThrow().toList()
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

    override suspend fun getLatLng(address: String): Result<LatLngData> =
        runCatching {
            naverRemoteDataSource.getLatLng(address).toDomain()
        }

    override suspend fun deleteReport(reportId: Long): Result<Unit> =
        runCatching {
            reportRemoteDataSource.deleteReport(reportId).handleBaseResponse().getOrThrow()
        }
}