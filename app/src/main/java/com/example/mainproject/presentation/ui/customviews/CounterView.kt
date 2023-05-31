package com.example.mainproject.presentation.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.mainproject.databinding.ViewCounterBinding

class CounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private val binding: ViewCounterBinding
    private var onChangeListener: () -> Unit = {}
    private var value: Int = 1
        set(value) {
            if (value > 1) {
                field = value
                binding.textCounter.text = field.toString()
                binding.minusCounter.isEnabled = true
                onChangeListener()
            } else {
                field = value
                binding.textCounter.text = field.toString()
                binding.minusCounter.isEnabled = false
                onChangeListener()
            }
        }

    init {
        binding = ViewCounterBinding.inflate(LayoutInflater.from(context), this, true)
        value = 1
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.minusCounter.setOnClickListener {
            value--
        }
        binding.plusCounter.setOnClickListener {
            value++
        }
    }

    fun getValue() = value

    fun setOnChangeListener(listener: () -> Unit) {
        onChangeListener = listener
    }
}