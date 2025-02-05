package com.example.findu.data.repositoryimpl

import android.content.Context
import android.net.Uri
import com.example.findu.data.dataremote.datasource.GptRemoteDataSource
import com.example.findu.data.dataremote.model.request.Content
import com.example.findu.data.dataremote.model.request.GptRequestDto
import com.example.findu.data.dataremote.model.request.RequestMessage
import com.example.findu.data.mapper.toDomain.toDomain
import com.example.findu.data.util.UriUtil.uriToBase64
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.repository.report.ReportRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val gptRemoteDataSource: GptRemoteDataSource,
    @ApplicationContext private val context: Context
) : ReportRepository {
    override suspend fun postImageAnalysis(imageUri: Uri): Result<GptData> {
        val request = GptRequestDto().apply {
            GptRequestDto.imageContent.imageUrl = imageUri.uriToBase64(context)
        }

        return runCatching { gptRemoteDataSource.postImagePrompt(request).toDomain() }
    }
}