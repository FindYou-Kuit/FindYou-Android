package com.example.findu.domain.model.search

import java.io.Serializable

data class SearchData(
    val cardId: Long,
    val thumbnailImageUrl: String,
    val title: String,
    var tag: SearchStatus,
    val date: String,
    val location: String,
    val interest: Boolean,
    val lastProtectId: Long,
    val lastReportId: Long,
    val isLast: Boolean
) : Serializable
