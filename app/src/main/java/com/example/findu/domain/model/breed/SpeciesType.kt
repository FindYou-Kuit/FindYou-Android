package com.example.findu.domain.model.breed

enum class SpeciesType(
    val displayName: String,
) {
    // 강아지 , 고양이 , 기타
    DOG("강아지"),
    CAT("고양이"),
    ETC("기타"),
    ;
    companion object {
        fun fromString(value: String): SpeciesType = SpeciesType.entries.first { it.displayName == value }
    }
}