package com.example.findu.data.mapper.todomain

import com.example.findu.data.dataremote.model.response.GptResponseDto
import com.example.findu.domain.model.report.FurColorType
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.model.breed.SpeciesType

fun GptResponseDto.toDomain(): GptData =
    this.choices.firstOrNull()?.message?.content?.let { content ->
        val parsedData = content.split(",")

        GptData(
            species = when (parsedData[0]) {
                SpeciesType.DOG.species -> SpeciesType.DOG
                SpeciesType.CAT.species -> SpeciesType.CAT
                else -> SpeciesType.ETC
            },
            breed = parsedData[1],
            furColors = parsedData.drop(2).map { color ->
                when (color) {
                    FurColorType.BLACK.color -> FurColorType.BLACK
                    FurColorType.WHITE.color -> FurColorType.WHITE
                    FurColorType.BROWN.color -> FurColorType.BROWN
                    FurColorType.GRAY.color -> FurColorType.GRAY
                    FurColorType.RED.color -> FurColorType.RED
                    FurColorType.YELLOW.color -> FurColorType.YELLOW
                    FurColorType.SPOTTED.color -> FurColorType.SPOTTED
                    else -> FurColorType.OTHER
                }
            }
        )
    } ?: GptData()
