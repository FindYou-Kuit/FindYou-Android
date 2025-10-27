package com.kuit.findu.presentation.type

import androidx.annotation.ColorRes
import com.kuit.findu.R

enum class AnimalStateType(
    val state: String,
    @ColorRes val textColor: Int,
    @ColorRes val backgroundChipColor: Int
) {
    PROTECT(
        state = "보호중",
        textColor = R.color.green1,
        backgroundChipColor = R.color.green2
    ),
    MISSING(
        state = "실종신고",
        textColor = R.color.red1,
        backgroundChipColor = R.color.red2
    ),
    FIND(
        state = "목격신고",
        textColor = R.color.blue1,
        backgroundChipColor = R.color.blue2
    );

    companion object {
        fun fromTag(tag: String): AnimalStateType =
            when (tag) {
                "보호중" -> PROTECT
                "실종신고" -> MISSING
                "목격신고" -> FIND
                else -> PROTECT
            }
    }
}