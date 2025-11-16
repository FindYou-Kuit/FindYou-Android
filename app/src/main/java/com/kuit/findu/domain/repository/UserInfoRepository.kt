package com.kuit.findu.domain.repository

interface UserInfoRepository {
    fun getDeviceId(): String
    fun setDeviceId(deviceId: String)
    fun getNickname(): String
    fun setNickname(nickname: String)
    fun clear()
}