package com.example.mainproject.presentation.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentCatalogBinding
import com.example.mainproject.presentation.ui.customviews.ProgressContainer
import com.example.mainproject.presentation.ui.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment() {

    private lateinit var binding: FragmentCatalogBinding
    private val viewModel: CatalogViewModel by viewModels()
    private val productAdapter = ProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarCatalog.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_profile -> {
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragmentContainerViewMain, ProfileFragment())
                        addToBackStack(null)
                    }
                    true
                }
                else -> false
            }
        }

        initRecyclerView()
        viewModel.updateProductList()

        viewModel.catalogUiState.observe(this as LifecycleOwner) { state ->
            when (state) {
                is CatalogUiState.Default -> {
                    binding.progressContainerCatalog.state = ProgressContainer.State.Success
                    productAdapter.setProducts(state.productList)
                }
                is CatalogUiState.Empty -> {
                    binding.progressContainerCatalog.state = ProgressContainer.State.Notice(
                        getString(R.string.progress_container_empty_title),
                        getString(R.string.progress_container_empty_description)
                    ) {
                        viewModel.updateProductList()
                    }
                }
                is CatalogUiState.Loading -> {
                    binding.progressContainerCatalog.state = ProgressContainer.State.Loading
                }
                is CatalogUiState.Error -> {
                    binding.progressContainerCatalog.state = ProgressContainer.State.Notice(
                        getString(R.string.progress_container_error_title),
                        getString(R.string.progress_container_error_description)
                    ) {
                        viewModel.updateProductList()
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewCatalog.adapter = productAdapter
        binding.recyclerViewCatalog.layoutManager =
            LinearLayoutManager(requireView().context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewCatalog.addItemDecoration(CatalogItemDecoration(binding.recyclerViewCatalog.context))
        ViewCompat.setOnApplyWindowInsetsListener(binding.recyclerViewCatalog) { view, insets ->
            val navigationBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.updatePadding(bottom = navigationBarInsets.bottom)
            insets
        }
    }
}