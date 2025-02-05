package com.example.findu.data.dataremote.service

import com.example.findu.BuildConfig
import com.example.findu.data.dataremote.model.request.GptRequestDto
import com.example.findu.data.dataremote.model.response.GptResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GptService {
    @POST("chat/completions") // API 엔드포인트에 맞게 설정
    suspend fun recognizeImage(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") authorization: String = BuildConfig.GPT_KEY,
        @Body request: GptRequestDto
    ): GptResponseDto
}