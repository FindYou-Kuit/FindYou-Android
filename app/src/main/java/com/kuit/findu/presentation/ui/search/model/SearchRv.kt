package com.kuit.findu.presentation.ui.search.model

import java.io.Serializable

data class SearchRv(
    val image : String,
    val name : String,
    val date : String,
    val location : String,
    var isBookmark : Boolean,
    var tag : SearchRvTag,
    val reportId: Long
) : Serializable
