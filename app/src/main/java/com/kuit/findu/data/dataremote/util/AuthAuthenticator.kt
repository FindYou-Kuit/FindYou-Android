package com.kuit.findu.data.dataremote.util

import com.kuit.findu.data.datalocal.datasource.TokenLocalDataSource
import com.kuit.findu.data.dataremote.model.request.TokenReissueRequestDto
import com.kuit.findu.data.dataremote.service.ReissueService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val reissueService: ReissueService,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // 401 Unauthorized 에러 감지
        if (response.code == 401) {
            return try {
                val refreshToken = tokenLocalDataSource.refreshToken

                // 토큰 재발급 요청
                val reissueResponse = runBlocking {
                    reissueService.postReissueToken(
                        TokenReissueRequestDto(refreshToken)
                    )
                }

                if (reissueResponse.success) {
                    // 새로운 토큰으로 저장
                    tokenLocalDataSource.accessToken = reissueResponse.data.accessToken
                    tokenLocalDataSource.refreshToken = reissueResponse.data.refreshToken

                    // 새로운 토큰으로 원래 요청 재시도
                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${reissueResponse.data.accessToken}")
                        .build()
                } else {
                    // 토큰 재발급 실패 - 로그인 화면으로 이동
                    tokenLocalDataSource.clearToken()
                    null
                }
            } catch (e: Exception) {
                // 토큰 재발급 중 오류 발생 - 로그인 화면으로 이동
                tokenLocalDataSource.clearToken()
                null
            }
        }

        return null
    }
}
