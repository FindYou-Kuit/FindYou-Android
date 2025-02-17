package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.GptRemoteDataSource
import com.example.findu.data.dataremote.datasource.ReportRemoteDataSource
import com.example.findu.data.dataremote.model.request.GptRequestDto
import com.example.findu.data.dataremote.model.request.GptRequestDto.Companion.imageContent
import com.example.findu.data.dataremote.model.request.ImageUrl
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.report.toDomain
import com.example.findu.data.mapper.todomain.toDomain
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.repository.report.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val gptRemoteDataSource: GptRemoteDataSource,
    private val reportRemoteDataSource: ReportRemoteDataSource
) : ReportRepository {
    override suspend fun postImageAnalysis(encodeString: String): Result<GptData> =
        runCatching {
            val request = GptRequestDto().apply {
                imageContent.imageUrl = ImageUrl(url = encodeString)
            }
            gptRemoteDataSource.postImagePrompt(request).toDomain()
        }

    override suspend fun uploadImages(images: List<String>): Result<List<Int>> =
        runCatching {
            reportRemoteDataSource.uploadImages(images).handleBaseResponse().getOrThrow().toDomain()
        }

}