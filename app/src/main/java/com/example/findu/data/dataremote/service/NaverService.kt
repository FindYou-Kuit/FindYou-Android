package com.example.findu.data.dataremote.service

import com.example.findu.BuildConfig
import com.example.findu.data.dataremote.model.response.report.GeocodeResponseDto
import com.example.findu.data.dataremote.model.response.report.ReverseGeocodeResponseDto
import com.example.findu.data.dataremote.util.ApiConstraints.GEOCODE
import com.example.findu.data.dataremote.util.ApiConstraints.REVERSE_GEOCODE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverService {
    @GET("/$REVERSE_GEOCODE")
    suspend fun getAddress(
        @Header("x-ncp-apigw-api-key-id") clientId: String = BuildConfig.NAVER_CLIENT_ID,
        @Header("x-ncp-apigw-api-key") clientSecret: String = BuildConfig.NAVER_CLIENT_SECRET,
        @Query("coords") coords: String,
        @Query("orders") orders: String = "roadaddr",
        @Query("output") output: String = "json",
    ): ReverseGeocodeResponseDto

    @GET("/$GEOCODE")
    suspend fun getLatLng(
        @Header("x-ncp-apigw-api-key-id") clientId: String = BuildConfig.NAVER_CLIENT_ID,
        @Header("x-ncp-apigw-api-key") clientSecret: String = BuildConfig.NAVER_CLIENT_SECRET,
        @Query("query") address: String,
        @Query("output") output: String = "json",
    ): GeocodeResponseDto
}