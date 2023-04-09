package com.example.mainproject.presentation.ui.product

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.mainproject.R
import com.example.mainproject.databinding.ViewHolderPreviewProductBinding

class ProductPreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderPreviewProductBinding.bind(itemView)

    fun bind(imageUrl: String) {
        binding.imageViewPreviewViewHolderProduct.load(imageUrl) {
            placeholder(R.drawable.ic_logo)
            error(R.drawable.ic_logo)
            transformations(RoundedCornersTransformation(itemView.resources.getDimension(R.dimen.image_view_holder_product_preview_border_radius)))
        }

        itemView.setOnClickListener {
            Toast.makeText(itemView.context, imageUrl, Toast.LENGTH_SHORT).show()
        }
    }
}