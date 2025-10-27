package com.kuit.findu.domain.model.search

import androidx.annotation.ColorRes
import com.kuit.findu.R
import java.io.Serializable

enum class SearchStatus(
    val text: String,
    @ColorRes val textColorRes: Int,
    @ColorRes val backgroundColorRes: Int
) : Serializable {
    PROTECTING("보호중", R.color.green1, R.color.green2),
    WITNESS("목격신고", R.color.blue1, R.color.blue2),
    MISSING("실종신고", R.color.red1, R.color.red2),
    UNKNOWN("기본", R.color.white, R.color.gray2)
}