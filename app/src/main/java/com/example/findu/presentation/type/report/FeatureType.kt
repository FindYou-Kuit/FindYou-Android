package com.example.findu.presentation.type.report

// TODO : Back 과 논의 후 특징 enum naming 정리 후 상수화
enum class PhysicalFeatureType(
    val feature: String
) {
    PUPPY("새끼 강아지에요"),
    ADULT("다 큰 성견이에요"),
    SMALL("소형견이에요"),
    MEDIUM("중형견이에요"),
    LARGE("대형견이에요")
}

enum class ExternalFeatureType(
    val feature: String
) {
    LEASH("목/가슴줄이 있어요"),
    WOUND("상처가 있어요"),
    FUR("털이 복슬복슬해요"),
    BALD("털이 거의 없어요"),
    LIMP("다리를 절뚝거려요")
}

enum class CharacterFeatureType(
    val feature: String
) {
    FRIENDLY("사람을 좋아해요"),
    RUNAWAY("계속 도망가요"),
    SCARED("겁이 많아요"),
    GENTLE("온순해요"),
    BARK("짖거나 울어요")
}

