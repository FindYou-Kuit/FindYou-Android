package com.example.findu.data.mapper.todomain.breed

import com.example.findu.data.dataremote.model.response.breed.BreedResponseDto
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.SpeciesType

fun List<BreedResponseDto>.toDomain(): BreedData {
    val dogBreedList = mutableListOf<BreedData.DogBreedData>()
    val catBreedList = mutableListOf<BreedData.CatBreedData>()
    val etcBreedList = mutableListOf<BreedData.EtcBreedData>()

    forEach { breedResponseDto ->
        when (breedResponseDto.toDomainSpecies()) {
            SpeciesType.DOG -> {
                dogBreedList.add(
                    BreedData.DogBreedData(
                        breedId = breedResponseDto.breedId,
                        breedName = breedResponseDto.breedName,
                        species = SpeciesType.CAT,
                    )
                )
            }

            SpeciesType.CAT -> {
                catBreedList.add(
                    BreedData.CatBreedData(
                        breedId = breedResponseDto.breedId,
                        breedName = breedResponseDto.breedName,
                        species = SpeciesType.CAT,
                    )
                )
            }

            SpeciesType.ETC -> {
                etcBreedList.add(
                    BreedData.EtcBreedData(
                        breedId = breedResponseDto.breedId,
                        breedName = breedResponseDto.breedName,
                        species = SpeciesType.CAT,
                    )
                )
            }
        }
    }
    return BreedData(
        dogBreedList = dogBreedList.toList(),
        catBreedList = catBreedList.toList(),
        etcBreedList = etcBreedList.toList()
    )
}

private fun BreedResponseDto.toDomainSpecies() =
    when (species) {
        "개" -> SpeciesType.DOG
        "고양이" -> SpeciesType.CAT
        else -> SpeciesType.ETC
    }


