package com.example.findu.presentation.model

import com.example.findu.domain.model.extra.Center
import com.example.findu.domain.model.extra.Department
import com.example.findu.domain.model.extra.VolunteerWork
sealed interface HomeExtraContent {
    data object None : HomeExtraContent
    data class Centers(val list: List<Center>) : HomeExtraContent
    data class Volunteers(val list: List<VolunteerWork>) : HomeExtraContent
    data class Departments(val list: List<Department>) : HomeExtraContent
}