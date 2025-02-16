package com.example.findu.domain.model.search

import java.io.Serializable

data class DetailSearchData(
    val imageUrl: String,
    val breed: String,
    val tag: String,
    val age: String,
    val weight: String,
    val sex: String,
    val happenDate: String,
    val furColor: String,
    val neutering: String,
    val significant: String,
    val noticeNumber: String,
    val noticeDuration: String,
    val foundLocation: String,
    val careName: String,
    val careAddr: String,
    val careTel: String,
    val authority: String,
    val authorityPhoneNumber: String,
    val interest: Boolean
) : Serializable

