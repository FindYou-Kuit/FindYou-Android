package com.kuit.findu.presentation.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kuit.findu.R

enum class HomeExtraButtonType(
    @DrawableRes val imageRes: Int,
    @StringRes val nameRes: Int
) {
    PROTECT_CENTER(
        imageRes = R.drawable.img_home_protect_center,
        nameRes = R.string.home_button_protect_center
    ),
    PROTECT_DEPARTMENT(
        imageRes = R.drawable.img_home_protect_part,
        nameRes = R.string.home_button_PROTECT_DEPARTMENT
    ),
    VOLUNTEER(
        imageRes = R.drawable.img_home_volunteer,
        nameRes = R.string.home_button_volunteer
    );
}