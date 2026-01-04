package com.kuit.findu.data.dataremote.util

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.recordException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ErrorTrackingInterceptor @Inject constructor(
    private val firebaseCrashlytics: FirebaseCrashlytics,
    private val discordLogger: DiscordLogger,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response

        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            firebaseCrashlytics.recordException(e)
            throw e
        }

        if (!response.isSuccessful) {
            val code = response.code
            val url = request.url.toString()

            val exceptionMessage = when (code) {
                in 400..499 -> "Client $code Error"
                in 500..599 -> "Server $code Error"
                else -> "Unknown $code Error"
            }

            firebaseCrashlytics.recordException(Exception(exceptionMessage)) {
                key("api_method", request.method)
                key("api_url", url)
                key("api_status", code)
            }

            if (code in 400..599) {
                CoroutineScope(Dispatchers.IO).launch {
                    discordLogger.logServerError(
                        code = code,
                        method = request.method,
                        url = url
                    )
                }
            }
        }

        return response
    }
}