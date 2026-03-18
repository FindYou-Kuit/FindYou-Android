package com.kuit.findu.data.dataremote.util

import com.kuit.findu.data.datalocal.datasource.TokenLocalDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthErrorInterceptor @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val sessionExpiredEventManager: SessionExpiredEventManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.code == 403) {
            tokenLocalDataSource.clearToken()
            sessionExpiredEventManager.notifySessionExpired()
        }

        return response
    }
}
