package com.example.findu.data.dataremote.service

import com.example.findu.BuildConfig
import com.example.findu.data.dataremote.model.response.report.NaverResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverService {
    @GET("/map-reversegeocode/v2/gc")
    suspend fun getAddress(
        @Header("x-ncp-apigw-api-key-id") clientId: String = BuildConfig.NAVER_CLIENT_ID,
        @Header("x-ncp-apigw-api-key") clientSecret: String = BuildConfig.NAVER_CLIENT_SECRET,
        @Query("coords") coords: String,
        @Query("output") output: String = "json",
    ): NaverResponseDto
}