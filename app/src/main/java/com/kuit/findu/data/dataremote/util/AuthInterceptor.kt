package com.kuit.findu.data.dataremote.util

import com.kuit.findu.data.datalocal.datasource.TokenLocalDataSource
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        return proceedWithAuthorization(chain, originalRequest)
    }

    private fun proceedWithAuthorization(chain: Interceptor.Chain, request: Request): Response {
        val authRequest = addAuthorizationHeader(request)
        return chain.proceed(authRequest)
    }

    private fun addAuthorizationHeader(request: Request): Request =
        request.newBuilder()
            .addHeader(AUTHORIZATION, "$BEARER ${tokenLocalDataSource.accessToken}")
            .build()

    companion object {
        private const val BEARER = "Bearer"
        private const val AUTHORIZATION = "Authorization"
    }
}