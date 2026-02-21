package com.kuit.findu.data.repositoryimpl

import com.kuit.findu.data.datalocal.datasource.TokenLocalDataSource
import com.kuit.findu.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {
    override fun getAccessToken(): String = tokenLocalDataSource.accessToken

    override fun setAccessToken(accessToken: String) {
        tokenLocalDataSource.accessToken = accessToken
    }

    override fun getRefreshToken(): String = tokenLocalDataSource.refreshToken

    override fun setRefreshToken(refreshToken: String) {
        tokenLocalDataSource.refreshToken = refreshToken
    }

    override fun clearToken() = tokenLocalDataSource.clearToken()
}