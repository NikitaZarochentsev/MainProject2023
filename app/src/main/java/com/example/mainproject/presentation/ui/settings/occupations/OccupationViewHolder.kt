package com.example.mainproject.presentation.ui.settings.occupations

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.databinding.ViewHolderSizeBinding

class OccupationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderSizeBinding.bind(itemView)

    fun bind(occupation: String) = binding.apply {
        buttonItemSize.text = occupation
    }
}