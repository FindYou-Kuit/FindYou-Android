package com.kuit.findu.data.dataremote.service

import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.base.NullableBaseResponse
import com.kuit.findu.data.dataremote.model.request.CheckNicknameRequestDto
import com.kuit.findu.data.dataremote.model.request.GuestLoginRequestDto
import com.kuit.findu.data.dataremote.model.request.LoginRequestDto
import com.kuit.findu.data.dataremote.model.response.CheckNicknameResponseDto
import com.kuit.findu.data.dataremote.model.response.auth.GuestLoginResponseDto
import com.kuit.findu.data.dataremote.model.response.auth.LoginResponseDto
import com.kuit.findu.data.dataremote.model.response.auth.UserInfoDto
import com.kuit.findu.data.dataremote.util.ApiConstraints.API
import com.kuit.findu.data.dataremote.util.ApiConstraints.AUTH
import com.kuit.findu.data.dataremote.util.ApiConstraints.VERSION
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthService {
    @POST("/$API/$VERSION/$AUTH/login/kakao")
    suspend fun postLogin(
        @Body loginRequestDto: LoginRequestDto
    ): NullableBaseResponse<LoginResponseDto>

    @POST("/$API/$VERSION/$AUTH/login/guest")
    suspend fun postGuestLogin(
        @Body guestLoginRequestDto: GuestLoginRequestDto
    ): NullableBaseResponse<GuestLoginResponseDto>

    @POST("/$API/$VERSION/users/check/duplicate-nickname")
    suspend fun postCheckNickname(
        @Body checkNicknameRequestDto: CheckNicknameRequestDto
    ): BaseResponse<CheckNicknameResponseDto>

    @Multipart
    @POST("/$API/$VERSION/users")
    suspend fun postSignup(
//        @Part profileImage: MultipartBody.Part?,
//        @Part("defaultProfileImageName") defaultImageName: RequestBody?,
        @Part("nickname") nickname: RequestBody,
        @Part("kakaoId") kakaoId: RequestBody,
        @Part("deviceId") deviceId: RequestBody
    ): NullableBaseResponse<UserInfoDto>
}