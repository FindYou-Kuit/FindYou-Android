package com.kuit.findu.data.dataremote.service

import com.kuit.findu.data.dataremote.model.response.DummyResponseDto
import retrofit2.http.GET

interface DummyService {
    @GET("/API")
    suspend fun dummy(): DummyResponseDto
}