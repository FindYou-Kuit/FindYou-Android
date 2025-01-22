package com.example.findu.presentation.ui.search.model

import com.google.android.material.chip.Chip
import java.io.Serializable

data class SearchData(
    val image : Int,
    val name : String,
    val date : String,
    val address : String,
    var isBookmark : Boolean,
    var status : String,
) : Serializable
