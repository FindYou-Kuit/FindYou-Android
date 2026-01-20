package com.kuit.findu.domain.repository

interface UserInfoRepository {
    fun getDeviceId(): String
    fun setDeviceId(deviceId: String)
    fun getNickname(): String
    fun setNickname(nickname: String)
    fun getIsGuestLogin(): Boolean
    fun setIsGuestLogin(isGuest: Boolean)
    fun clear()
}