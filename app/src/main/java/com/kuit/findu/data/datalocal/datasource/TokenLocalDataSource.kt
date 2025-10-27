package com.kuit.findu.data.datalocal.datasource

interface TokenLocalDataSource {
    var accessToken: String
    var refreshToken: String
    fun clearToken()
}