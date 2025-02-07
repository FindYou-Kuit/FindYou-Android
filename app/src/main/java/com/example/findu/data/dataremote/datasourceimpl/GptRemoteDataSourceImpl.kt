package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.GptRemoteDataSource
import com.example.findu.data.dataremote.model.request.GptRequestDto
import com.example.findu.data.dataremote.model.response.GptResponseDto
import com.example.findu.data.dataremote.service.GptService
import javax.inject.Inject

class GptRemoteDataSourceImpl @Inject constructor(
    private val gptService: GptService
) : GptRemoteDataSource {
    override suspend fun postImagePrompt(
        request: GptRequestDto
    ): GptResponseDto =
        gptService.recognizeImage(request = request)
}