package com.example.findu.data.datalocal.datasource

interface DeviceLocalDataSource {
    var deviceId: String
    var nickname: String

    fun clear()
}