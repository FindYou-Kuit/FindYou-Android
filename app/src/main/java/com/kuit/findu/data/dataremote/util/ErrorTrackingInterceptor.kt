package com.kuit.findu.data.dataremote.util

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.recordException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ErrorTrackingInterceptor @Inject constructor(
    private val firebaseCrashlytics: FirebaseCrashlytics,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response

        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            // 1. 네트워크 자체가 끊긴 경우 등 (IOException)
            firebaseCrashlytics.recordException(e)
            throw e
        }

        // 2. 서버에서 응답은 왔으나 4xx, 5xx 에러인 경우
        if (!response.isSuccessful) {
            val code = response.code

            val url = request.url.toString()

            // Crashlytics에 상세 정보 기록
            val exceptionMessage = when (response.code) {
                in (400..499) -> "Client $code Error"
                in (500..599) -> "Server $code Error"
                else -> "Unknown $code Error"
            }
            firebaseCrashlytics.recordException(Exception(exceptionMessage)) {
                key("api_method", request.method)
                key("api_url", url)
                key("api_status", code)
            }
        }

        return response
    }
}