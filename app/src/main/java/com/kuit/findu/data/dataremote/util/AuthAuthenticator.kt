package com.kuit.findu.data.dataremote.util

import android.content.Context
import android.content.Intent
import com.kuit.findu.data.datalocal.datasource.TokenLocalDataSource
import com.kuit.findu.presentation.ui.login.LoginActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    @ApplicationContext private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // 401 Unauthorized 에러 감지
        if (response.code == 401) {
            // 토큰 삭제
            tokenLocalDataSource.clearToken()

            // 로그인 화면으로 이동
            val intent = Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(intent)
        }

        return response
    }
}
