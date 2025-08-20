package com.example.findu.presentation.type

import androidx.annotation.DrawableRes
import com.example.findu.R

enum class HomeBannerType (
    @DrawableRes val imgRes: Int,
    val page: Int
) {
    ADOPT(
        imgRes = R.drawable.img_home_banner_adopt,
        page = 1
    ),
    VOLUNTEER(
        imgRes = R.drawable.img_home_banner_volunteer,
        page = 2
    ),
    REPORT(
        imgRes = R.drawable.img_home_banner_report,
        page = 3
    )
}