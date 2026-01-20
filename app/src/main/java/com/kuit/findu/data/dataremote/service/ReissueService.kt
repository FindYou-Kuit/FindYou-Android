package com.kuit.findu.data.dataremote.service

import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.request.TokenReissueRequestDto
import com.kuit.findu.data.dataremote.model.response.auth.TokenReissueResponseDto
import com.kuit.findu.data.dataremote.util.ApiConstraints.API
import com.kuit.findu.data.dataremote.util.ApiConstraints.AUTH
import com.kuit.findu.data.dataremote.util.ApiConstraints.VERSION
import retrofit2.http.Body
import retrofit2.http.POST

interface ReissueService {

    @POST("/$API/$VERSION/$AUTH/reissue/token")
    suspend fun postReissueToken(
        @Body tokenReissueRequestDto: TokenReissueRequestDto,
    ): BaseResponse<TokenReissueResponseDto>
}