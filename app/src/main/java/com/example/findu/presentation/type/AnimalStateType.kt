package com.example.findu.presentation.type

import androidx.annotation.ColorRes
import com.example.findu.R

enum class AnimalStateType(
    val state: String,
    @ColorRes val textColor: Int,
    @ColorRes val backgroundChipColor: Int
) {
    Protect(
        state = "보호중",
        textColor = R.color.green1,
        backgroundChipColor = R.color.green2
    ),
    Missing(
        state = "실종신고",
        textColor = R.color.red1,
        backgroundChipColor = R.color.red2
    ),
    Find(
        state = "목격신고",
        textColor = R.color.blue1,
        backgroundChipColor = R.color.blue2
    )
}