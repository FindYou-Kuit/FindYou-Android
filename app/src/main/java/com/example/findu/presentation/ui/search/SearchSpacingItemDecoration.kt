package com.example.findu.presentation.ui.search

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SearchSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
            val index = layoutParams.spanIndex
            if (index == 0) {
                outRect.right = spacing / 2
            } else {
                outRect.left = spacing / 2
            }
        } else {
            outRect.top = spacing / 2
            outRect.bottom = spacing / 2
        }
    }
}
