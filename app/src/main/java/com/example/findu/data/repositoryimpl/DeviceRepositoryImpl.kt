package com.example.findu.data.repositoryimpl

import com.example.findu.data.datalocal.datasource.DeviceLocalDataSource
import com.example.findu.domain.repository.DeviceRepository
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val deviceLocalDataSource: DeviceLocalDataSource
) : DeviceRepository {
    override fun getDeviceId(): String = deviceLocalDataSource.deviceId

    override fun setDeviceId(deviceId: String) {
        deviceLocalDataSource.deviceId = deviceId
    }

    override fun clear() = deviceLocalDataSource.clear()
}