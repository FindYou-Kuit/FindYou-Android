package com.example.findu.data.datalocal.datasourceimpl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.findu.data.datalocal.datasource.TokenLocalDataSource
import com.example.findu.di.qualifier.TokenPrefs
import javax.inject.Inject

class TokenLocalDataSourceImpl @Inject constructor(
    @TokenPrefs private val sharedPreferences: SharedPreferences
) : TokenLocalDataSource {
    override var accessToken: String
        get() = sharedPreferences.getString(ACCESS_TOKEN, INITIAL_VALUE) ?: INITIAL_VALUE
        set(value) = sharedPreferences.edit { putString(ACCESS_TOKEN, value) }

    override var refreshToken: String
        get() = sharedPreferences.getString(REFRESH_TOKEN, INITIAL_VALUE) ?: INITIAL_VALUE
        set(value) = sharedPreferences.edit { putString(REFRESH_TOKEN, value) }

    override fun clearToken() {
        sharedPreferences.edit { clear() }
    }

    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val INITIAL_VALUE = ""

    }
}