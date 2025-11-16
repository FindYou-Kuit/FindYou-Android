package com.kuit.findu.data.mapper.torequest

import com.kuit.findu.data.dataremote.model.request.AiDetectionRequestDto

fun String.toAiDetectionRequest() = AiDetectionRequestDto(
    base64Image = this
)