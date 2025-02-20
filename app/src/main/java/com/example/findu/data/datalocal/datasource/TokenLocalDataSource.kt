package com.example.findu.data.datalocal.datasource

interface TokenLocalDataSource {
    var accessToken: String
    fun clearInfo()
}