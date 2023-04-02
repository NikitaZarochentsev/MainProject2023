package com.example.mainproject.presentation.ui.catalog

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.mainproject.R
import com.example.mainproject.domain.models.Product
import com.example.mainproject.databinding.ViewHolderProductBinding

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderProductBinding.bind(itemView)

    fun bind(product: Product) {
        binding.textViewHeaderProduct.text = product.name
        binding.textViewDescriptionProduct.text = product.description
        binding.textViewCostProduct.text =
            itemView.resources.getString(R.string.cost_rubles, product.cost.toString())
        binding.imageViewProduct.load(product.iconURL) {
            transformations(
                RoundedCornersTransformation(
                    itemView.resources.getDimension(R.dimen.image_view_product_border_radius)
                )
            )
        }

        itemView.setOnClickListener {
            Toast.makeText(itemView.context, product.name, Toast.LENGTH_SHORT).show()
        }

        binding.buttonBuyProduct.setOnClickListener {
            Toast.makeText(
                itemView.context,
                itemView.resources.getString(R.string.cost_rubles, product.cost.toString()),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}