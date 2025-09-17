package com.example.findu.domain.repository

interface DeviceRepository {
    fun getDeviceId(): String
    fun setDeviceId(deviceId: String)
    fun clear()
}