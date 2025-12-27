package com.kuit.findu.data.dataremote.util

import com.kuit.findu.data.dataremote.service.WebhookService
import com.kuit.findu.domain.model.DiscordLogBody
import com.kuit.findu.domain.repository.UserInfoRepository
import retrofit2.HttpException
import javax.inject.Inject

class DiscordLogger @Inject constructor(
    private val webhookService: WebhookService,
    private val userInfoRepository: UserInfoRepository,
    private val webhookUrl: String,
) {

    suspend fun logServerError(
        code: Int,
        method: String,
        url: String,
    ) {
        val deviceId = userInfoRepository.getDeviceId()
        val nickname = userInfoRepository.getNickname()

        val body = DiscordLogBody(
            content = """
            🚨 **Server Error 발생**
            
            👤 User
            - Nickname: $nickname
            - DeviceId: $deviceId
            
            🌐 Request
            - Method: $method
            - Url: $url
            - Code: $code
            """.trimIndent()
        )

        runCatching {
            webhookService.sendLog(webhookUrl, body)
        }
    }
}