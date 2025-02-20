package com.example.findu.domain.repository

interface TokenRepository {
    fun getAccessToken(): String
    fun setTokens(accessToken: String)
    fun clearInfo()
}