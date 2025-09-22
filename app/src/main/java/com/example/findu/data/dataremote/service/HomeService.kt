package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.HomeResponseDto
import com.example.findu.data.dataremote.util.ApiConstraints.API
import com.example.findu.data.dataremote.util.ApiConstraints.HOME
import com.example.findu.data.dataremote.util.ApiConstraints.VERSION
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("/$API/$VERSION/$HOME")
    suspend fun getHome(
        @Query("lat") lat: Double? = null,
        @Query("lon") lon: Double? = null
    ): BaseResponse<HomeResponseDto>
}