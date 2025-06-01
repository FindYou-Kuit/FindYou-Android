package com.example.findu.domain.repository

interface TokenRepository {
    fun getAccessToken(): String
    fun setAccessToken(accessToken: String)
    fun getRefreshToken(): String
    fun setRefreshToken(refreshToken: String)
    fun clearToken()
}