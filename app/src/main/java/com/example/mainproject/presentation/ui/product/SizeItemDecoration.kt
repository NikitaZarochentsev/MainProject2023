package com.example.mainproject.presentation.ui.product

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SizeItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = 0
        outRect.bottom = 0
    }
}