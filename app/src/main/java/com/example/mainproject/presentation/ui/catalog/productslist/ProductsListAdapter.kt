package com.example.mainproject.presentation.ui.catalog.productslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R
import com.example.mainproject.domain.models.Product

class ProductsListAdapter : RecyclerView.Adapter<ProductViewHolder>() {

    private var products = mutableListOf<Product>()
    private lateinit var onClickListener: OnClickListener

    interface OnClickListener {
        fun onItemClicked(productId: String)
        fun onButtonBuyClicked(
            imageUrl: String,
            productSize: String,
            productTitle: String,
            productDepartment: String,
            productPrice: Int,
            productId: String,
        )
    }

    fun setOnCLickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val viewHolder = ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_product, parent, false)
        )
        viewHolder.buttonBuy.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            val currentProduct = products[position]
            val availableSize = currentProduct.sizes.find { it.isAvailable }
            if (availableSize != null) {
                val productTitle = parent.context.getString(
                    R.string.new_order_product_title,
                    availableSize.value,
                    currentProduct.title
                )
                onClickListener.onButtonBuyClicked(
                    currentProduct.preview,
                    currentProduct.sizes[0].value,
                    productTitle,
                    currentProduct.department,
                    currentProduct.price,
                    currentProduct.id
                )
            }
        }
        viewHolder.itemView.setOnClickListener {
            onClickListener.onItemClicked(products[viewHolder.bindingAdapterPosition].id)
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun setProducts(products: List<Product>) {
        val productsListDiffCallback = ProductsListDiffCallback(this.products, products)
        val diffResult = DiffUtil.calculateDiff(productsListDiffCallback)
        val newProducts = mutableListOf<Product>()
        newProducts.addAll(products)
        this.products = newProducts
        diffResult.dispatchUpdatesTo(this)
    }
}