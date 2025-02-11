package com.example.findu.data.mapper.todomain.breed

import com.example.findu.data.dataremote.model.response.breed.BreedResponseDto
import com.example.findu.data.dataremote.model.response.breed.BreedValidationResponseDto
import com.example.findu.domain.model.breed.Breed
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.BreedValidationData
import com.example.findu.domain.model.breed.SpeciesType

fun List<BreedResponseDto>.toDomain(): BreedData {
    val dogBreedList = mutableListOf<Breed.DogBreed>()
    val catBreedList = mutableListOf<Breed.CatBreed>()
    val etcBreedList = mutableListOf<Breed.EtcBreed>()

    forEach { breedResponseDto ->
        when (breedResponseDto.toDomainSpecies()) {
            SpeciesType.DOG -> {
                dogBreedList.add(
                    Breed.DogBreed(
                        breedId = breedResponseDto.breedId,
                        breedName = breedResponseDto.breedName,
                        species = SpeciesType.CAT,
                    )
                )
            }

            SpeciesType.CAT -> {
                catBreedList.add(
                    Breed.CatBreed(
                        breedId = breedResponseDto.breedId,
                        breedName = breedResponseDto.breedName,
                        species = SpeciesType.CAT,
                    )
                )
            }

            SpeciesType.ETC -> {
                etcBreedList.add(
                    Breed.EtcBreed(
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

fun BreedValidationResponseDto.toDomain() =
    BreedValidationData(
        breedId = breedId,
        isExist = isExist
    )

