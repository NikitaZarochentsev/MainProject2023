package com.example.mainproject.presentation.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R
import com.example.mainproject.domain.models.Product

class CatalogAdapter(private val parentFragmentManager: FragmentManager) :
    RecyclerView.Adapter<ItemCatalogViewHolder>() {

    private var products = listOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCatalogViewHolder {
        return ItemCatalogViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_holder_item_catalog,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ItemCatalogViewHolder, position: Int) {
        holder.bind(products[position], parentFragmentManager)
    }

    fun setProducts(products: List<Product>) {
        val itemCatalogDiffCallback = ItemCatalogDiffCallback(this.products, products)
        val diffResult = DiffUtil.calculateDiff(itemCatalogDiffCallback)
        this.products = products
        diffResult.dispatchUpdatesTo(this)
    }
}