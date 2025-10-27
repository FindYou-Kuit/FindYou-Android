package com.kuit.findu.presentation.type

import androidx.annotation.StringRes
import com.kuit.findu.R

enum class HomeUserStatusType(
    @StringRes val protectAnimalListTitleRes: Int,
    @StringRes val reportedAnimalListTitleRes: Int
) {
    MEMBER(
        protectAnimalListTitleRes = R.string.home_protect_animal_list_title_member,
        reportedAnimalListTitleRes = R.string.home_reported_animal_list_title_member
    ),

    GUEST(
        protectAnimalListTitleRes = R.string.home_protect_animal_list_title_guest,
        reportedAnimalListTitleRes = R.string.home_reported_animal_list_title_guest
    ),

    LOCATION_DENIED(
        protectAnimalListTitleRes = R.string.home_protect_animal_list_title_location_denied,
        reportedAnimalListTitleRes = R.string.home_reported_animal_list_title_location_denied
    )
}