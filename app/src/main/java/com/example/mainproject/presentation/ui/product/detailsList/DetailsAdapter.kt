package com.example.mainproject.presentation.ui.product.detailsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R

class DetailsAdapter : RecyclerView.Adapter<DetailViewHolder>() {

    private var details = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_detail_product, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return details.size
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(details[position])
    }

    fun setDetails(details: List<String>) {
        val detailsDiffCallback = DetailsDiffCallback(this.details, details)
        this.details = details
        val diffResult = DiffUtil.calculateDiff(detailsDiffCallback)
        diffResult.dispatchUpdatesTo(this)
    }
}