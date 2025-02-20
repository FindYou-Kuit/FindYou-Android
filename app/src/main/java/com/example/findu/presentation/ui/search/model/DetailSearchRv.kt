package com.example.findu.presentation.ui.search.model

data class DetailSearchRv(
    val imageUrl : String,
    val breed : String,
    val tag : SearchRvTag,
    val age : String,
    val weight : String,
    val sex : String,
    val happenDate : String,
    val furColor : String,
    val neutering : String,
    val significant : String,
    val noticeNumber : String,
    val noticeDuration : String,
    val foundLocation : String,
    val careName : String,
    val careAddr : String,
    val careTel : String,
    val authority : String,
    val authorityPhoneNumber : String,
    var interest : Boolean
)
