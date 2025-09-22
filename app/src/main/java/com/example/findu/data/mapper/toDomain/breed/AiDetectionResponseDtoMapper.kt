package com.example.findu.data.mapper.todomain.breed

import com.example.findu.data.dataremote.model.response.breed.AiDetectionResponseDto
import com.example.findu.domain.model.breed.AiDetectionData

fun AiDetectionResponseDto.toDomain() = AiDetectionData(
    species = species,
    breed = breed,
    furColors = furColors
)