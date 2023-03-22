package com.example.mainproject.presentation.ui.catalog

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R

class ProductItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val divider = ContextCompat.getDrawable(context, R.drawable.divider_product)!!
    private val normalOffset = context.resources.getDimension(R.dimen.normal_100).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.let { adapter ->
            val childAdapterPosition = parent.getChildAdapterPosition(view)
            if (childAdapterPosition == 0) {
                outRect.top = normalOffset
            }
            if (childAdapterPosition == adapter.itemCount - 1) {
                outRect.bottom = normalOffset
            } else {
                outRect.bottom = divider.intrinsicHeight
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let { adapter ->
            parent.children.forEach { view ->
                val childAdapterPosition = parent.getChildAdapterPosition(view)
                if (childAdapterPosition != adapter.itemCount - 1) {
                    val left = parent.paddingLeft + normalOffset
                    val top = view.bottom
                    val right = left + view.right - parent.paddingRight - (2 * normalOffset)
                    val bottom = top + divider.intrinsicHeight
                    divider.bounds = Rect(left, top, right, bottom)
                    divider.draw(c)
                }
            }
        }
    }
}