package com.example.mainproject.presentation.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentSizeBottomBinding
import com.example.mainproject.domain.models.Size
import com.example.mainproject.presentation.ui.product.sizes.SizesAdapter
import com.example.mainproject.presentation.ui.product.sizes.SizesItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SizesBottomFragment(private val sizes: List<Size>) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSizeBottomBinding
    private val sizesAdapter = SizesAdapter()

    companion object {
        const val REQUEST_KEY = "SELECTED_ITEM"
        const val RESPONSE_KEY = "SIZE"
    }

    override fun getTheme(): Int {
        return R.style.Theme_MainProject_BottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSizeBottomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewSizeBottom.adapter = sizesAdapter
        binding.recyclerViewSizeBottom.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewSizeBottom.addItemDecoration(SizesItemDecoration())
        sizesAdapter.setSizes(sizes)
        sizesAdapter.setOnItemClickListener { size ->
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(RESPONSE_KEY to size))
            requireDialog().cancel()
        }
    }
}