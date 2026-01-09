package com.kuit.findu.data.datalocal.datasource

interface DeviceLocalDataSource {
    var deviceId: String
    var nickname: String
    var isGuestLogin: Boolean

    fun clear()
}