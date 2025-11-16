package com.kuit.findu.data.mapper.todomain.breed

import com.kuit.findu.data.dataremote.model.response.breed.BreedResponseDto
import com.kuit.findu.data.dataremote.model.response.breed.BreedValidationResponseDto
import com.kuit.findu.domain.model.breed.Breed
import com.kuit.findu.domain.model.breed.BreedData
import com.kuit.findu.domain.model.breed.BreedValidationData
import com.kuit.findu.domain.model.breed.SpeciesType

fun BreedResponseDto.toDomain(): BreedData {
    val dogBreedList = dogBreedList.mapIndexed { index, breedName ->
        Breed.DogBreed(
            breedId = index + 1, // 임시 ID 생성 (1부터 시작)
            breedName = breedName,
            species = SpeciesType.DOG
        )
    }

    val catBreedList = catBreedList.mapIndexed { index, breedName ->
        Breed.CatBreed(
            breedId = dogBreedList.size + index + 1, // DOG ID 다음부터 시작
            breedName = breedName,
            species = SpeciesType.CAT
        )
    }

    val etcBreedList = etcBreedList.mapIndexed { index, breedName ->
        Breed.EtcBreed(
            breedId = dogBreedList.size + catBreedList.size + index + 1, // CAT ID 다음부터 시작
            breedName = breedName,
            species = SpeciesType.ETC
        )
    }

    return BreedData(
        dogBreedList = dogBreedList,
        catBreedList = catBreedList,
        etcBreedList = etcBreedList
    )
}


fun BreedValidationResponseDto.toDomain() =
    BreedValidationData(
        breedId = breedId,
        isExist = isExist
    )

