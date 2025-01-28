package com.example.findu.presentation.ui.search.model

import com.example.findu.R
import java.io.Serializable

enum class SearchStatus(
    val text: String,
    val textColor: Int,
    val backgroundRes: Int
) : Serializable {
    PROTECTING("보호중", R.color.green1, R.drawable.bg_search_protecting_tag),
    WITNESS("목격신고", R.color.blue1, R.drawable.bg_search_report_tag),
    MISSING("실종신고", R.color.red1, R.drawable.bg_search_lost_tag)
}