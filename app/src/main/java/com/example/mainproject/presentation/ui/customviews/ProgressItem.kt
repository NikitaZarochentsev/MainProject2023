package com.example.mainproject.presentation.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import com.example.mainproject.R
import com.example.mainproject.databinding.ViewProgressItemBinding
import kotlin.properties.Delegates

private const val MAX_CHILD_COUNT = 3

class ProgressItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewProgressItemBinding

    init {
        binding = ViewProgressItemBinding.inflate(LayoutInflater.from(context), this)
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        if (childCount > MAX_CHILD_COUNT) {
            throw IllegalStateException(context.getString(R.string.child_progress_item_exception_text))
        }
    }

    private fun findContentView(): View? =
        children.firstOrNull {
            it.id != R.id.layoutLoading && it.id != R.id.layoutNotice
        }

    sealed class State {
        object Loading : State()
        data class Notice(val title: String, val buttonOnClick: () -> Unit) : State()
        object Success : State()
    }

    var state: State by Delegates.observable(State.Loading) { _, _, state ->
        when (state) {
            is State.Success -> {
                binding.layoutLoading.isVisible = false
                binding.layoutNotice.isVisible = false
                findContentView()?.isVisible = true
            }
            is State.Loading -> {
                binding.layoutLoading.isVisible = true
                binding.layoutNotice.isVisible = false
                findContentView()?.isVisible = false
            }
            is State.Notice -> {
                binding.layoutLoading.isVisible = false
                binding.layoutNotice.isVisible = true
                findContentView()?.isVisible = false
                binding.textViewNotice.text = state.title
                binding.buttonNotice.setOnClickListener {
                    state.buttonOnClick()
                }
            }
        }
    }
}