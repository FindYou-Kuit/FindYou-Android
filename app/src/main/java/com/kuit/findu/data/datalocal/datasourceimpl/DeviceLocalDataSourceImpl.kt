package com.kuit.findu.data.datalocal.datasourceimpl

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.kuit.findu.data.datalocal.datasource.DeviceLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeviceLocalDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : DeviceLocalDataSource {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override var deviceId: String
        get() = sharedPreferences.getString(DEVICE_ID, INITIAL_VALUE).toString()
        set(value) = sharedPreferences.edit { putString(DEVICE_ID, value) }


    override var nickname: String
        get() = sharedPreferences.getString(NICKNAME, INITIAL_VALUE).toString()
        set(value) = sharedPreferences.edit { putString(NICKNAME, value) }

    override var isGuestLogin: Boolean
        get() = sharedPreferences.getBoolean(IS_GUEST_LOGIN, false)
        set(value) = sharedPreferences.edit { putBoolean(IS_GUEST_LOGIN, value) }

    override fun clear() {
        val currentDeviceId = deviceId
        sharedPreferences.edit {
            clear()
            putString(DEVICE_ID, currentDeviceId)
        }
    }
    private companion object {
        const val PREFERENCES_NAME = "device_preferences"
        const val DEVICE_ID = "deviceId"
        const val NICKNAME = "nickname"
        const val IS_GUEST_LOGIN = "isGuestLogin"

        const val INITIAL_VALUE = ""
    }
}