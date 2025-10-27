package com.kuit.findu.presentation.type.report

enum class PhysicalFeatureType(
    val feature: String,
    val featureId: Int
) {
    PUPPY("새끼 강아지에요", 1),
    ADULT("다 큰 성견이에요", 2),
    SMALL("소형견이에요", 3),
    MEDIUM("중형견이에요", 4),
    LARGE("대형견이에요", 5)
}

enum class ExternalFeatureType(
    val feature: String,
    val featureId: Int
) {
    LEASH("목/가슴줄이 있어요", 6),
    WOUND("상처가 있어요", 7),
    FUR("털이 복슬복슬해요", 8),
    BALD("털이 거의 없어요", 9),
    LIMP("다리를 절뚝거려요", 10)
}

enum class CharacterFeatureType(
    val feature: String,
    val featureId: Int
) {
    FRIENDLY("사람을 좋아해요", 11),
    RUNAWAY("계속 도망가요", 12),
    SCARED("겁이 많아요", 13),
    GENTLE("온순해요", 14),
    BARK("짖거나 울어요", 15)
}

data class ReportFeature(
    val feature: String,
    val featureId: Int
)

