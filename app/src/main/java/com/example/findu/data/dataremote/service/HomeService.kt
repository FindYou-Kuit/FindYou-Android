package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.HomeResponseDto
import retrofit2.http.GET

interface HomeService {
    @GET("/api/v1/home/data")
    suspend fun getHome(): BaseResponse<HomeResponseDto>
}