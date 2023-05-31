package com.example.mainproject.presentation.ui.catalog.productslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R

class FooterProductsListAdapter : RecyclerView.Adapter<FooterProductsListViewHolder>() {

    sealed class FooterState {
        object Loading : FooterState()
        object Notice : FooterState()
        object Empty : FooterState()
    }

    var state: FooterState = FooterState.Loading

    var titleError = ""
    lateinit var onRetryButtonClickListener: () -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FooterProductsListViewHolder {
        return FooterProductsListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_footer_products_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: FooterProductsListViewHolder, position: Int) {
        when (state) {
            is FooterState.Loading -> {
                holder.bind(FooterProductsListViewHolder.ItemLastState.Loading)
            }
            is FooterState.Notice -> {
                holder.bind(
                    FooterProductsListViewHolder.ItemLastState.Notice(
                        titleError,
                        onRetryButtonClickListener
                    )
                )
            }
            is FooterState.Empty -> {
                holder.bind(FooterProductsListViewHolder.ItemLastState.Empty)
            }
        }
    }

    fun setEmpty() {
        state = FooterState.Empty
        notifyItemChanged(0)
    }

    fun setError(title: String, onButtonClickListener: () -> Unit) {
        titleError = title
        onRetryButtonClickListener = onButtonClickListener
        state = FooterState.Notice
        notifyItemChanged(0)
    }

    fun setLoading() {
        state = FooterState.Loading
        notifyItemChanged(0)
    }
}