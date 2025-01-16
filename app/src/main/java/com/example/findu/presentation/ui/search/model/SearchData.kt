package com.example.findu.presentation.ui.search.model

import java.io.Serializable

data class SearchData(
    val image : Int,
    val name : String,
    val date : String,
    val address : String,
) : Serializable
