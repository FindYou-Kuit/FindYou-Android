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
    private val sessionExpiredEventManager: SessionExpiredEventManager,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val staleToken = tokenLocalDataSource.accessToken

        synchronized(LOCK) {
            val currentToken = tokenLocalDataSource.accessToken

            if (currentToken != staleToken) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $currentToken")
                    .build()
            }

            return try {
                val refreshToken = tokenLocalDataSource.refreshToken

                val reissueResponse = runBlocking {
                    reissueService.postReissueToken(
                        TokenReissueRequestDto(refreshToken)
                    )
                }

                if (reissueResponse.success) {
                    tokenLocalDataSource.accessToken = reissueResponse.data.accessToken
                    tokenLocalDataSource.refreshToken = reissueResponse.data.refreshToken

                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${reissueResponse.data.accessToken}")
                        .build()
                } else {
                    tokenLocalDataSource.clearToken()
                    sessionExpiredEventManager.notifySessionExpired()
                    null
                }
            } catch (e: Exception) {
                tokenLocalDataSource.clearToken()
                sessionExpiredEventManager.notifySessionExpired()
                null
            }
        }
    }

    companion object {
        private val LOCK = Any()
    }
}
