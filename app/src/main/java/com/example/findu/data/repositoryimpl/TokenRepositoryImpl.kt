package com.example.findu.data.repositoryimpl

import com.example.findu.data.datalocal.datasource.TokenLocalDataSource
import com.example.findu.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : TokenRepository {
    override fun getAccessToken(): String = tokenLocalDataSource.accessToken

    override fun setTokens(accessToken: String) {
        tokenLocalDataSource.accessToken = accessToken
    }

    override fun clearInfo() = tokenLocalDataSource.clearInfo()
}