package com.kuit.findu.data.dataremote.service

import com.kuit.findu.domain.model.DiscordLogBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface WebhookService {
    @POST
    suspend fun sendLog(
        @Url webhookUrl: String,
        @Body body: DiscordLogBody
    )
}