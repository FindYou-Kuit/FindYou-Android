package com.kuit.findu.data.datalocal.datasource

interface DummyLocalDataSource {
    var token: String
    var nickname: String
    fun clear()
}