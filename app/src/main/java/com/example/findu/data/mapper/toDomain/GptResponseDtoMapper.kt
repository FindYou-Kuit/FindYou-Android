package com.example.findu.data.mapper.toDomain

import com.example.findu.data.dataremote.model.response.GptResponseDto
import com.example.findu.domain.model.GptData
import kotlinx.serialization.json.Json

fun GptResponseDto.toDomain(): GptData {
    return this.choices.firstOrNull()?.message?.content?.let {
        Json.decodeFromString<GptData>(it)
    } ?: GptData()
}