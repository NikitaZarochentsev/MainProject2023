package com.example.mainproject.presentation.ui.catalog

import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.mainproject.R
import com.example.mainproject.domain.models.Product
import com.example.mainproject.databinding.ViewHolderItemCatalogBinding
import com.example.mainproject.presentation.ui.product.ProductFragment

class ItemCatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderItemCatalogBinding.bind(itemView)

    fun bind(product: Product, parentFragmentManager: FragmentManager) {
        binding.textViewHeaderItemCatalog.text = product.title
        binding.textViewDescriptionItemCatalog.text = product.department
        binding.textViewCostItemCatalog.text =
            itemView.resources.getString(R.string.cost_rubles, product.price.toString())
        binding.imageViewItemCatalog.load(product.preview) {
            transformations(
                RoundedCornersTransformation(
                    itemView.resources.getDimension(R.dimen.image_product_border_radius)
                )
            )
        }

        binding.buttonBuyItemCatalog.setOnClickListener {
            Toast.makeText(
                itemView.context,
                itemView.resources.getString(R.string.cost_rubles, product.price.toString()),
                Toast.LENGTH_SHORT
            ).show()
        }

        itemView.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(
                    R.id.fragmentContainerViewMain,
                    ProductFragment.newInstance(product.id)
                )
                addToBackStack(null)
            }
        }
    }
}