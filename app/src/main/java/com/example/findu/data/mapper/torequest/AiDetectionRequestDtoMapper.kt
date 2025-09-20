package com.example.findu.data.mapper.torequest

import com.example.findu.data.dataremote.model.request.AiDetectionRequestDto

fun String.toAiDetectionRequest() = AiDetectionRequestDto(
    imageUrl = this
)