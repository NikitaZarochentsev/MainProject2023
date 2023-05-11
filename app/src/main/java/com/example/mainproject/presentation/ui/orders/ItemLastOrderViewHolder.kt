package com.example.mainproject.presentation.ui.orders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.databinding.ViewHolderItemLastOrdersBinding
import com.example.mainproject.presentation.ui.customviews.ProgressItem

class ItemLastOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderItemLastOrdersBinding.bind(itemView)

    sealed class ItemLastState {
        object Loading : ItemLastState()
        data class Notice(val title: String, val buttonOnClick: () -> Unit) : ItemLastState()
    }

    fun bind(state: ItemLastState) {
        when (state) {
            is ItemLastState.Loading -> {
                binding.progressItemOrder.state = ProgressItem.State.Loading
            }
            is ItemLastState.Notice -> {
                binding.progressItemOrder.state =
                    ProgressItem.State.Notice(state.title, state.buttonOnClick)
            }
        }
    }
}