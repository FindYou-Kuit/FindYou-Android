package com.kuit.findu.analytics

data class AnalyticsEvent(
    val type: String,
    val extras: List<Param> = emptyList(),
) {
    data class Param(
        val key: String,
        val value: String,
    )

    companion object {
        const val SCREEN_VIEW = "screen_view" // TYPE
        const val SCREEN_NAME = "screen_name" // EXTRA_KEY

        // AdMob Events
        const val AD_CLICK = "ad_click"
        const val AD_IMPRESSION = "ad_impression"
        const val AD_LOCATION = "ad_location"
    }
}