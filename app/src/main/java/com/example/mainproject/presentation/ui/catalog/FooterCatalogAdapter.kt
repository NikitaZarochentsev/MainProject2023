package com.example.mainproject.presentation.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R

class FooterCatalogAdapter : RecyclerView.Adapter<ItemLastCatalogViewHolder>() {

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
    ): ItemLastCatalogViewHolder {
        return ItemLastCatalogViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_item_last_catalog, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if (state is FooterState.Empty) 0 else 1
    }

    override fun onBindViewHolder(holder: ItemLastCatalogViewHolder, position: Int) {
        when (state) {
            is FooterState.Loading -> {
                holder.bind(ItemLastCatalogViewHolder.ItemLastState.Loading)
            }
            is FooterState.Notice -> {
                holder.bind(
                    ItemLastCatalogViewHolder.ItemLastState.Notice(
                        titleError,
                        onRetryButtonClickListener
                    )
                )
            }
            else -> {
                // остается пустое состояние, но в нем этот метод вызываться не должен
            }
        }
    }

    fun remove() {
        notifyItemRemoved(0)
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