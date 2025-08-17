package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.request.CheckNicknameRequestDto
import com.example.findu.data.dataremote.model.request.GuestLoginRequestDto
import com.example.findu.data.dataremote.model.request.LoginRequestDto
import com.example.findu.data.dataremote.model.request.SignupRequestDto
import com.example.findu.data.dataremote.model.response.CheckNicknameResponseDto
import com.example.findu.data.dataremote.model.response.auth.GuestLoginResponseDto
import com.example.findu.data.dataremote.model.response.auth.LoginResponseDto
import com.example.findu.data.dataremote.util.ApiConstraints.API
import com.example.findu.data.dataremote.util.ApiConstraints.AUTH
import com.example.findu.data.dataremote.util.ApiConstraints.VERSION
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/$API/$VERSION/$AUTH/login/kakao")
    suspend fun postLogin(
        @Body loginRequestDto: LoginRequestDto
    ): BaseResponse<LoginResponseDto>

    @POST("/$API/$VERSION/$AUTH/login/guest")
    suspend fun postGuestLogin(
        @Body guestLoginRequestDto: GuestLoginRequestDto
    ): BaseResponse<GuestLoginResponseDto>

    @POST("/$API/$VERSION/users/check/duplicate-nickname")
    suspend fun postCheckNickname(
        @Body checkNicknameRequestDto: CheckNicknameRequestDto
    ): BaseResponse<CheckNicknameResponseDto>

    @POST("/$API/$VERSION/$AUTH/signup")
    suspend fun postSignup(
        @Body signupRequestBody: SignupRequestDto
    ): Response<Unit>
}