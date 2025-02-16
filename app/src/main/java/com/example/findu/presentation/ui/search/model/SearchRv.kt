package com.example.findu.presentation.ui.search.model

import java.io.Serializable

data class SearchRv(
    val image : String,
    val name : String,
    val date : String,
    val address : String,
    var isBookmark : Boolean,
    var status : SearchRvTag,
) : Serializable
