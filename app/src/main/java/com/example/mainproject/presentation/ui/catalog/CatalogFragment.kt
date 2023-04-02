package com.example.mainproject.presentation.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainproject.R
import com.example.mainproject.domain.models.Product
import com.example.mainproject.databinding.FragmentCatalogBinding
import com.example.mainproject.presentation.MainProjectApplication
import com.example.mainproject.presentation.customviews.ProgressContainer
import com.example.mainproject.presentation.ui.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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

        binding.recyclerViewCatalog.adapter = productAdapter
        binding.recyclerViewCatalog.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewCatalog.addItemDecoration(CatalogItemDecoration(binding.recyclerViewCatalog.context))
        showData(listOf())

        viewModel.productListUiState.observe(this as LifecycleOwner) { result ->
            result
                .onSuccess {
                    showData(it.productList)
                }
                .onFailure {
                    binding.progressContainerCatalog.state = ProgressContainer.State.Notice(
                        getString(R.string.progress_container_error_title),
                        getString(R.string.progress_container_error_description)
                    ) {
                        updateData()
                    }
                }
        }
    }

    private fun updateData() {
        binding.progressContainerCatalog.state = ProgressContainer.State.Loading
        viewModel.updateProductListUiState()
    }

    private fun showData(data: List<Product>) {
        if (data.isEmpty()) {
            binding.progressContainerCatalog.state = ProgressContainer.State.Notice(
                getString(R.string.progress_container_empty_title),
                getString(R.string.progress_container_empty_description)
            ) {
                updateData()
            }
        } else {
            binding.progressContainerCatalog.state = ProgressContainer.State.Success
            productAdapter.setProducts(data)
        }
    }
}