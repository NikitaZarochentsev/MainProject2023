package com.example.mainproject.presentation.ui.settings.occupations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R

class OccupationAdapter : RecyclerView.Adapter<OccupationViewHolder>() {

    private var occupations = listOf<String>()
    private lateinit var onItemClickListener: OnItemClickListener

    fun interface OnItemClickListener {
        fun onItemClicked(occupation: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OccupationViewHolder {
        val viewHolder = OccupationViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_size, parent, false)
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            onItemClickListener.onItemClicked(occupations[position])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return occupations.size
    }

    override fun onBindViewHolder(holder: OccupationViewHolder, position: Int) {
        holder.bind(occupations[position])
    }

    fun setOccupations(occupations: List<String>) {
        this.occupations = occupations
        for (index in 0..this.occupations.size) {
            notifyItemInserted(index)
        }
    }
}