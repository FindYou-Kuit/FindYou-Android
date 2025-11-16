package com.kuit.findu.data.dataremote.datasource

import com.kuit.findu.data.dataremote.model.request.GptRequestDto
import com.kuit.findu.data.dataremote.model.response.GptResponseDto

interface GptRemoteDataSource {
    suspend fun postImagePrompt(
        request: GptRequestDto
    ) : GptResponseDto
}