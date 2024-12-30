package com.example.findu.data.datalocal.datasource

interface DummyLocalDataSource {
    var token: String
    var nickname: String
    fun clear()
}