package com.example.mainproject.presentation.ui.product.preview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PreviewItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = 10
        outRect.right = 10
        outRect.top = 0
        outRect.bottom = 0
    }
}