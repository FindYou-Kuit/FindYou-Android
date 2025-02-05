package com.example.findu.data.mapper.toDomain

import com.example.findu.data.dataremote.model.response.GptResponseDto
import com.example.findu.domain.model.report.Breed
import com.example.findu.domain.model.report.FurColorType
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.model.report.SpeciesType
import kotlinx.serialization.json.Json

fun GptResponseDto.toDomain(): GptData {
    return this.choices.firstOrNull()?.message?.content?.let {
        val split = it.split(",")

        GptData(
            breed = Breed(split[0]),
            species = when (split[1]) {
                "강아지" -> SpeciesType.DOG
                "고양이" -> SpeciesType.CAT
                else -> SpeciesType.ETC
            },
            furColors = split.subList(2, split.size).map {
                when (it) {
                    "검은색" -> FurColorType.BLACK
                    "하얀색" -> FurColorType.WHITE
                    "갈색" -> FurColorType.BROWN
                    "회색" -> FurColorType.GRAY
                    "적색" -> FurColorType.RED
                    "노란색" -> FurColorType.YELLOW
                    "점박이" -> FurColorType.SPOTTED
                    "줄무늬" -> FurColorType.STRIPED
                    else -> FurColorType.OTHER
                }
            }
//            split.forEachIndexed { index, color ->
//                if (index < 2) return@forEachIndexed
//                when (color) {
//                    "검은색" -> FurColorType.BLACK
//                    "하얀색" -> FurColorType.WHITE
//                    "갈색" -> FurColorType.BROWN
//                    "회색" -> FurColorType.GRAY
//                    "적색" -> FurColorType.RED
//                    "노란색" -> FurColorType.YELLOW
//                    "점박이" -> FurColorType.SPOTTED
//                    "줄무늬" -> FurColorType.STRIPED
//                    else -> FurColorType.OTHER
//                }
//            }.toList()
        )
    } ?: GptData()
}