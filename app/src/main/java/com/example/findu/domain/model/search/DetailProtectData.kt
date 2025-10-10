package com.example.findu.domain.model.search

import java.io.Serializable

data class DetailProtectData(
    val imageUrl: String,
    val breed: String,
    val tag: SearchStatus,
    val age: String,
    val weight: String,
    val sex: String,
    val happenDate: String,
    val furColor: String,
    val neutering: String,
    val specialNote: String,
    val noticeNumber: String,
    val noticeDuration: String,
    val foundLocation: String,
    val careName: String,
    val careAddr: String,
    val careTel: String,
    val authority: String,
    val authorityPhoneNumber: String,
    var interest: Boolean
) : Serializable

