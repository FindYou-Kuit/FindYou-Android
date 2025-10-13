package com.example.findu.presentation.ui.search.adapter

import com.example.findu.presentation.ui.search.model.SearchRv

interface SearchListListener {
    fun onFilterClick()
    fun onToggleClick()
    fun onItemClick(item: SearchRv)
    fun onBookmarkClick(id: Long, isBookmark: Boolean, tag: String)
    fun onBannerClick()
    fun getBannerRes(): Int
}