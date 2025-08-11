package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.AuthRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.toDomain
import com.example.findu.data.mapper.torequest.toRequestDto
import com.example.findu.domain.model.CheckEmailData
import com.example.findu.domain.model.LoginData
import com.example.findu.domain.model.LoginInfo
import com.example.findu.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun postLogin(loginInfo: LoginInfo): Result<LoginData> =
        runCatching {
            authRemoteDataSource.postLogin(loginRequestDto = loginInfo.toRequestDto()).handleBaseResponse().getOrThrow().toDomain()
        }

    override suspend fun postCheckEmail(email: String): Result<CheckEmailData> =
        runCatching {
            authRemoteDataSource.postCheckEmail(email).handleBaseResponse().getOrThrow().toDomain()
        }

    override suspend fun postSignup(
        email: String,
        password: String,
        nickname: String
    ): Result<String> =
        runCatching {
            val response: Response<Unit> =
                authRemoteDataSource.postSignup(email, password, nickname)

            if (response.isSuccessful) {
                val accessToken = response.headers()["Authorization"]?.removePrefix("Bearer ")
                if (!accessToken.isNullOrEmpty()) {
                    return@runCatching accessToken
                } else {
                    throw Exception("Access Token이 응답 헤더에 없음")
                }
            } else {
                throw Exception("회원가입 실패: ${response.code()}")
            }
        }
}