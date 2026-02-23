package com.kuit.findu.data.dataremote.util

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.recordException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
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
            val errorBody = response.peekBody(MAX_ERROR_BODY_BYTES).string()
            val errorMessage = extractErrorMessage(errorBody)

            val exceptionMessage = when (code) {
                in 400..499 -> "Client $code Error"
                in 500..599 -> "Server $code Error"
                else -> "Unknown $code Error"
            }

            firebaseCrashlytics.recordException(Exception(exceptionMessage)) {
                key("api_method", request.method)
                key("api_url", url)
                key("api_status", code)
                errorMessage?.let { key("api_message", it) }
            }

            if (code in 400..599) {
                CoroutineScope(Dispatchers.IO).launch {
                    discordLogger.logServerError(
                        code = code,
                        method = request.method,
                        url = url,
                        reason = errorMessage ?: errorBody.takeIf { it.isNotBlank() }
                    )
                }
            }
        }

        return response
    }

    private fun extractErrorMessage(errorBody: String): String? {
        if (errorBody.isBlank()) return null
        return runCatching {
            JSONObject(errorBody).optString("message")
        }.getOrNull()?.takeIf { it.isNotBlank() }
    }

    companion object {
        private const val MAX_ERROR_BODY_BYTES = 1024L * 1024L // 1MB snapshot for logging
    }
}
