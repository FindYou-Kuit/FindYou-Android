package com.example.findu.presentation.util.kakao

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

object KakaoLoginHelper {
    fun login(context: Context, callback: (OAuthToken?, Throwable?) -> Unit) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback =  callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback= callback)
        }
    }
}