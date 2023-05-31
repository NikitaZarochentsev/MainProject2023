package com.example.mainproject.presentation.ui.catalog.productslist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.mainproject.R
import com.example.mainproject.databinding.ViewHolderProductBinding
import com.example.mainproject.domain.models.Product

class ProductViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderProductBinding.bind(itemView)
    val buttonBuy = binding.buttonBuyItemCatalog

    fun bind(product: Product) = binding.apply {
        textViewHeaderItemCatalog.text = product.title
        textViewDescriptionItemCatalog.text = product.department
        textViewCostItemCatalog.text =
            itemView.resources.getString(
                R.string.cost_rubles,
                product.price.toString()
            )
        imageViewItemCatalog.load(product.preview) {
            transformations(
                RoundedCornersTransformation(
                    itemView.resources.getDimension(R.dimen.normal_border_radius_100)
                )
            )
        }
    }
}