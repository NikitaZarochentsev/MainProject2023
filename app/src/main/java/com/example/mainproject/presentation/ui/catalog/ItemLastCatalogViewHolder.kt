package com.example.mainproject.presentation.ui.catalog

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.databinding.ViewHolderItemLastCatalogBinding
import com.example.mainproject.presentation.ui.customviews.ProgressItem

class ItemLastCatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderItemLastCatalogBinding.bind(itemView)

    sealed class ItemLastState {
        object Loading : ItemLastState()
        data class Notice(val title: String, val buttonOnClick: () -> Unit) : ItemLastState()
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
        }
    }
}