package com.example.findu.data.mapper.todomain.breed

import com.example.findu.data.dataremote.model.response.breed.BreedResponseDto
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.CatData
import com.example.findu.domain.model.breed.DogData
import com.example.findu.domain.model.breed.EtcData
import com.example.findu.domain.model.breed.SpeciesType

fun List<BreedResponseDto>.toDomain() =
    BreedData().apply {
        forEach { breedResponseDto ->
            when (breedResponseDto.toDomainSpecies()) {
                SpeciesType.DOG -> {
                    this.dogList.plus(
                        DogData(
                            breedId = breedResponseDto.breedId,
                            breedName = breedResponseDto.breedName,
                            species = SpeciesType.CAT,
                        )
                    )
                }

                SpeciesType.CAT -> {
                    this.catList.plus(
                        CatData(
                            breedId = breedResponseDto.breedId,
                            breedName = breedResponseDto.breedName,
                            species = SpeciesType.CAT,
                        )
                    )
                }

                SpeciesType.ETC -> {
                    this.etcList.plus(
                        EtcData(
                            breedId = breedResponseDto.breedId,
                            breedName = breedResponseDto.breedName,
                            species = SpeciesType.CAT,
                        )
                    )
                }
            }
        }
    }

private fun BreedResponseDto.toDomainSpecies() =
    when (species) {
        "개" -> SpeciesType.DOG
        "고양이" -> SpeciesType.CAT
        else -> SpeciesType.ETC
    }


)
