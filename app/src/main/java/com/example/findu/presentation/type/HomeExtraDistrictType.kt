package com.example.findu.presentation.type

import androidx.annotation.StringRes
import com.example.findu.R

enum class HomeExtraDistrictType(
    @StringRes val stringRes: Int,
) {
    DISTRICT_TYPE_SIDO(
        stringRes = R.string.home_extra_sido,
    ),
    DISTRICT_TYPE_SIGUNGU(
        stringRes = R.string.home_extra_sigungu,
    ),
}

