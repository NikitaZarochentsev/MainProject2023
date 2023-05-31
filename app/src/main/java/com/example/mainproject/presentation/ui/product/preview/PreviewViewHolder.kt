package com.example.mainproject.presentation.ui.product.preview

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.mainproject.R
import com.example.mainproject.databinding.ViewHolderPreviewProductBinding

class PreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderPreviewProductBinding.bind(itemView)

    fun bind(imageUrl: String, select: Boolean) = binding.apply {
        imageViewPreviewViewHolderProduct.load(imageUrl) {
            placeholder(R.drawable.ic_logo)
            error(R.drawable.ic_logo)
            transformations(RoundedCornersTransformation(itemView.resources.getDimension(R.dimen.normal_border_radius_50)))
        }



        imageViewPreviewViewHolderProduct.background =
            if (select) ContextCompat.getDrawable(
                itemView.context,
                R.drawable.border_item_recycler_view
            ) else null
    }
}