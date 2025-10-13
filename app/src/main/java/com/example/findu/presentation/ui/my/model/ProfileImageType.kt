package com.example.findu.presentation.ui.my.model

import androidx.annotation.DrawableRes
import com.example.findu.R


enum class ProfileImageType(
    val serverName: String,
    @DrawableRes val drawableRes: Int
) {
    PUPPY("puppy", R.drawable.img_my_profile1),
    CHICK("chick", R.drawable.img_my_profile2),
    PANDA("panda", R.drawable.img_my_profile3),
    DEFAULT("default", R.drawable.img_my_profile_default);

    companion object {
        fun fromServerName(name: String?): ProfileImageType {
            return entries.find { it.serverName == name } ?: DEFAULT
        }
    }
}