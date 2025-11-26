package com.kuit.findu.analytics

fun AnalyticsHelper.logScreenView(screenName: String) {
    logEvent(
        AnalyticsEvent(
            type = AnalyticsEvent.SCREEN_VIEW,
            extras = listOf(
                AnalyticsEvent.Param(AnalyticsEvent.SCREEN_NAME, screenName),
            ),
        ),
    )
}

fun AnalyticsHelper.logUserSignIn(userName: String, type: String) {
    logEvent(
        AnalyticsEvent(
            type = "signed_in",
            extras = listOf(
                AnalyticsEvent.Param("user_name", userName),
                AnalyticsEvent.Param("login_type", type),
            ),
        ),
    )
}

fun AnalyticsHelper.logUserSignUp(userName: String) {
    logEvent(
        AnalyticsEvent(
            type = "signed_in",
            extras = listOf(
                AnalyticsEvent.Param("user_name", userName),
                AnalyticsEvent.Param("login_type", "Kakao"),
            ),
        ),
    )
}
