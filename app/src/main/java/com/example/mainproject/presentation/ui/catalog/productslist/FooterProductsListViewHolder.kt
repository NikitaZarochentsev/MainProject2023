package com.example.mainproject.presentation.ui.catalog.productslist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.databinding.ViewHolderFooterProductsListBinding
import com.example.mainproject.presentation.ui.customviews.ProgressItem

class FooterProductsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderFooterProductsListBinding.bind(itemView)

    sealed class ItemLastState {
        object Loading : ItemLastState()
        data class Notice(val title: String, val buttonOnClick: () -> Unit) : ItemLastState()
        object Empty : ItemLastState()
    }

    fun bind(state: ItemLastState) {
        when (state) {
            is ItemLastState.Loading -> {
                binding.progressItemCatalog.state = ProgressItem.State.Loading
            }
            is ItemLastState.Notice -> {
                binding.progressItemCatalog.state =
                    ProgressItem.State.Notice(state.title, state.buttonOnClick)
            }
            is ItemLastState.Empty -> {
                binding.progressItemCatalog.state = ProgressItem.State.Success
            }
        }
    }
}