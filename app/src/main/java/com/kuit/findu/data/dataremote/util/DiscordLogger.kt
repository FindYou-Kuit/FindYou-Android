package com.kuit.findu.data.dataremote.util

import com.kuit.findu.data.dataremote.service.WebhookService
import com.kuit.findu.domain.model.DiscordLogBody
import com.kuit.findu.domain.repository.UserInfoRepository
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
        reason: String? = null,
    ) {
        val deviceId = userInfoRepository.getDeviceId()
        val nickname = userInfoRepository.getNickname()
        val sanitizedReason = reason?.takeIf { it.isNotBlank() }
        val content = buildString {
            appendLine("🚨 **Server Error 발생**")
            appendLine()
            appendLine("👤 User")
            appendLine("- Nickname: $nickname")
            appendLine("- DeviceId: $deviceId")
            appendLine()
            appendLine("🌐 Request")
            appendLine("- Method: $method")
            appendLine("- Url: $url")
            append("- Code: $code")
            sanitizedReason?.let {
                appendLine()
                append("- Reason: $it")
            }
        }.trim()

        val body = DiscordLogBody(content = content)

        runCatching {
            webhookService.sendLog(webhookUrl, body)
        }
    }
}
