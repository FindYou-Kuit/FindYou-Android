package com.example.findu.domain.model.search

import java.io.Serializable

data class SearchData(
    val image : Int,
    val name : String,
    val date : String,
    val address : String,
    var isBookmark : Boolean,
    var status : SearchStatus,
) : Serializable
