package com.kuit.findu.domain.model.my

sealed class MyProfileImageUpdate {
    data class FilePath(val path: String) : MyProfileImageUpdate()
    data class Default(val name: String) : MyProfileImageUpdate()
}