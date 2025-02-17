package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.request.GptRequestDto
import com.example.findu.data.dataremote.model.response.GptResponseDto

interface GptRemoteDataSource {
    suspend fun postImagePrompt(
        request: GptRequestDto
    ) : GptResponseDto
}