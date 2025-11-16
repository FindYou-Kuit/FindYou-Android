package com.kuit.findu.data.repositoryimpl

import com.kuit.findu.data.datalocal.datasource.DeviceLocalDataSource
import com.kuit.findu.domain.repository.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val deviceLocalDataSource: DeviceLocalDataSource
) : UserInfoRepository {
    override fun getDeviceId(): String = deviceLocalDataSource.deviceId

    override fun setDeviceId(deviceId: String) {
        deviceLocalDataSource.deviceId = deviceId
    }

    override fun getNickname(): String = deviceLocalDataSource.nickname

    override fun setNickname(nickname: String) {
        deviceLocalDataSource.nickname = nickname
    }

    override fun clear() = deviceLocalDataSource.clear()
}