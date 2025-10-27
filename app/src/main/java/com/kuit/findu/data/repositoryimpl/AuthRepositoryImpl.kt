package com.kuit.findu.data.repositoryimpl

import com.kuit.findu.data.dataremote.datasource.AuthRemoteDataSource
import com.kuit.findu.data.dataremote.util.handleBaseResponse
import com.kuit.findu.data.mapper.todomain.toDomain
import com.kuit.findu.data.mapper.torequest.toRequestDto
import com.kuit.findu.domain.model.GuestLoginData
import com.kuit.findu.domain.model.LoginData
import com.kuit.findu.domain.model.LoginInfo
import com.kuit.findu.domain.model.UserInfo
import com.kuit.findu.domain.repository.AuthRepository
import java.io.File
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun postLogin(loginInfo: LoginInfo): Result<LoginData> =
        runCatching {
            authRemoteDataSource.postLogin(loginRequestDto = loginInfo.toRequestDto()).handleBaseResponse().getOrThrow()
                ?.toDomain() ?: error("Login data is null")
        }

    override suspend fun postGuestLogin(deviceId: String): Result<GuestLoginData> =
        runCatching {
            authRemoteDataSource.postGuestLogin(guestLoginRequestDto = deviceId.toRequestDto()).handleBaseResponse()
                .getOrThrow()
                ?.toDomain() ?: error("Login data is null")
        }

    override suspend fun postCheckNickname(nickname: String): Result<Boolean> =
        runCatching {
            authRemoteDataSource.postCheckNickname(nickname = nickname).handleBaseResponse().getOrThrow().isDuplicate
        }

    override suspend fun postSignup(
        profileImageFile: File?,
        defaultImageName: String?,
        nickname: String,
        kakaoId: Long,
        deviceId: String
    ): Result<UserInfo> = runCatching {
        authRemoteDataSource.postSignup(
            profileImageFile = profileImageFile,
            defaultImageName = defaultImageName,
            nickname = nickname,
            kakaoId = kakaoId,
            deviceId = deviceId
        ).handleBaseResponse().getOrThrow()?.toDomain() ?: error("Signup data is null")
    }
}