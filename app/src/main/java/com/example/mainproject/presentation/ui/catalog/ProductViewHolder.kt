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
import com.example.mainproject.databinding.ViewHolderProductBinding
import com.example.mainproject.presentation.ui.product.ProductFragment

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderProductBinding.bind(itemView)

    fun bind(product: Product, parentFragmentManager: FragmentManager) {
        binding.textViewHeaderProduct.text = product.name
        binding.textViewDescriptionProduct.text = product.description
        binding.textViewCostProduct.text =
            itemView.resources.getString(R.string.cost_rubles, product.cost.toString())
        binding.imageViewProduct.load(product.iconURL) {
            transformations(
                RoundedCornersTransformation(
                    itemView.resources.getDimension(R.dimen.image_product_border_radius)
                )
            )
        }

        binding.buttonBuyProduct.setOnClickListener {
            Toast.makeText(
                itemView.context,
                itemView.resources.getString(R.string.cost_rubles, product.cost.toString()),
                Toast.LENGTH_SHORT
            ).show()
        }

        itemView.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(
                    R.id.fragmentContainerViewMain,
                    ProductFragment.newInstance(product.productId)
                )
                addToBackStack(null)
            }
        }
    }
}