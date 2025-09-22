package com.example.findu.presentation.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.findu.R

enum class HomeButtonType(
    @DrawableRes val imageRes: Int,
    @StringRes val nameRes: Int
) {
    PROTECT_CENTER(
        imageRes = R.drawable.img_home_protect_center,
        nameRes = R.string.home_button_protect_center
    ),
    HOSPITAL(
        imageRes = R.drawable.img_home_hospital,
        nameRes = R.string.home_button_hospital
    ),
    PROTECT_PART(
        imageRes = R.drawable.img_home_protect_part,
        nameRes = R.string.home_button_protect_part
    ),
    VOLUNTEER(
        imageRes = R.drawable.img_home_volunteer,
        nameRes = R.string.home_button_volunteer
    );
}