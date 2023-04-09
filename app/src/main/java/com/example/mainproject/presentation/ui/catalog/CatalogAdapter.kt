package com.example.mainproject.presentation.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R
import com.example.mainproject.domain.models.Product

class ProductAdapter(private val parentFragmentManager: FragmentManager) :
    RecyclerView.Adapter<ProductViewHolder>() {

    private var productsList = listOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_holder_product,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productsList[position], parentFragmentManager)
    }

    fun setProducts(productsList: List<Product>) {
        val productDiffCallback = ProductDiffCallback(this.productsList, productsList)
        val diffResult = DiffUtil.calculateDiff(productDiffCallback)
        this.productsList = productsList
        diffResult.dispatchUpdatesTo(this)
    }
}