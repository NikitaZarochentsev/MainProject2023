package com.example.mainproject.presentation.ui.orders.orderslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R

class FooterOrdersListAdapter : RecyclerView.Adapter<FooterOrdersListViewHolder>() {

    sealed class FooterState {
        object Loading : FooterState()
        object Notice : FooterState()
        object Empty : FooterState()
    }

    var state: FooterState = FooterState.Loading
    var titleError = ""
    lateinit var onRetryButtonClickListener: () -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterOrdersListViewHolder {
        return FooterOrdersListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_footer_orders_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: FooterOrdersListViewHolder, position: Int) {
        when (state) {
            is FooterState.Loading -> {
                holder.bind(FooterOrdersListViewHolder.ItemLastState.Loading)
            }
            is FooterState.Notice -> {
                holder.bind(
                    FooterOrdersListViewHolder.ItemLastState.Notice(
                        titleError,
                        onRetryButtonClickListener
                    )
                )
            }
            is FooterState.Empty -> {
                holder.bind(FooterOrdersListViewHolder.ItemLastState.Empty)
            }
        }
    }

    fun setEmpty() {
        state = FooterState.Empty
        notifyItemChanged(0)
    }

    fun setError(title: String, onButtonCLickListener: () -> Unit) {
        titleError = title
        onRetryButtonClickListener = onButtonCLickListener
        state = FooterState.Notice
        notifyItemChanged(0)
    }

    fun setLoading() {
        state = FooterState.Loading
        notifyItemChanged(0)
    }
}