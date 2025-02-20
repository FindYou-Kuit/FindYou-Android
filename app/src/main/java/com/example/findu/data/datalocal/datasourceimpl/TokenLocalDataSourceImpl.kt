package com.example.findu.data.datalocal.datasourceimpl

import android.content.SharedPreferences
import com.example.findu.data.datalocal.datasource.TokenLocalDataSource
import com.example.findu.di.qualifier.TokenPrefs
import javax.inject.Inject

class TokenLocalDataSourceImpl @Inject constructor(
    @TokenPrefs private val sharedPreferences: SharedPreferences
) : TokenLocalDataSource {
    override var accessToken: String
        get() = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
        set(value) = sharedPreferences.edit().putString(ACCESS_TOKEN, value).apply()

    override fun clearInfo() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }
}