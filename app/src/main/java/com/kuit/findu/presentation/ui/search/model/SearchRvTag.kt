package com.kuit.findu.presentation.ui.search.model

import com.kuit.findu.R
import java.io.Serializable

enum class SearchRvTag(
    val text: String,
    val textColor: Int,
    val backgroundRes: Int,
    val dateTag: String
) : Serializable {
    PROTECTING("보호중", R.color.green1, R.drawable.bg_search_protecting_tag, "발견날짜: "),
    WITNESS("목격신고", R.color.blue1, R.drawable.bg_search_report_tag, "신고날짜: "),
    MISSING("실종신고", R.color.red1, R.drawable.bg_search_lost_tag, "신고날짜: "),
    UNKNOWN("기본",R.color.white, R.drawable.bg_search_protecting_tag, "")
}