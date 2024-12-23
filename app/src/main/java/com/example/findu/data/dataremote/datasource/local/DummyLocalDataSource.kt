package com.example.findu.data.dataremote.datasource.local

interface DummyLocalDataSource {
    var token: String
    var nickname: String
    fun clear()
}