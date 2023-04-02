package com.example.mainproject.presentation.ui.profile

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mainproject.databinding.ViewHolderSettingBinding

class SettingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderSettingBinding.bind(itemView)

    fun bind(setting: Setting) {
        binding.imageViewSetting.setImageResource(setting.icon)
        binding.textViewSetting.text = setting.title
    }
}