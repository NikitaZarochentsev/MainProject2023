package com.example.mainproject.presentation.ui.customviews

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.mainproject.R
import com.example.mainproject.databinding.ViewProgressButtonBinding
import kotlin.properties.Delegates

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewProgressButtonBinding

    init {
        binding = ViewProgressButtonBinding.inflate(LayoutInflater.from(context), this, true)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressButton,
            0,
            0
        ).apply {
            binding.button.text = getString(R.styleable.ProgressButton_text)
        }
    }

    var isLoading: Boolean by Delegates.observable(false) { _, _, isLoading ->
        binding.progressBar.isVisible = isLoading

        binding.button.isClickable = !isLoading
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            binding.button.textScaleX = if (isLoading) 0f else 1f
        } else {
            binding.button.textSize = 0f
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.button.setOnClickListener(l)
    }
}