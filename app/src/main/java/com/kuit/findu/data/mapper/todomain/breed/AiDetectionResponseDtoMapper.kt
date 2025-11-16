package com.kuit.findu.data.mapper.todomain.breed

import com.kuit.findu.data.dataremote.model.response.breed.AiDetectionResponseDto
import com.kuit.findu.domain.model.breed.AiDetectionData

fun AiDetectionResponseDto.toDomain() = AiDetectionData(
    species = species,
    breed = breed,
    furColors = furColors
)