package com.kuit.findu.data.dataremote.service

import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.response.home.HomeResponseDto
import com.kuit.findu.data.dataremote.util.ApiConstraints
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("/${ApiConstraints.API}/${ApiConstraints.VERSION}/${ApiConstraints.HOME}")
    suspend fun getHome(
        @Query("lat") lat: Double? = null,
        @Query("lon") lon: Double? = null,
    ): BaseResponse<HomeResponseDto>
}