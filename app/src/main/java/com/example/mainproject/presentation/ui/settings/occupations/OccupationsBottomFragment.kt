package com.example.mainproject.presentation.ui.settings.occupations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentSizeBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OccupationsBottomFragment(private val occupations: List<String>) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSizeBottomBinding
    private val occupationsAdapter = OccupationAdapter()

    companion object {
        const val REQUEST_KEY = "SELECTED_ITEM"
        const val RESPONSE_KEY = "OCCUPATION"
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
        binding.recyclerViewSizeBottom.adapter = occupationsAdapter
        binding.recyclerViewSizeBottom.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewSizeBottom.addItemDecoration(OccupationItemDecoration())
        occupationsAdapter.setOccupations(occupations)
        occupationsAdapter.setOnItemClickListener { occupation ->
            parentFragmentManager.setFragmentResult(
                REQUEST_KEY,
                bundleOf(RESPONSE_KEY to occupation)
            )
            requireDialog().cancel()
        }
    }
}