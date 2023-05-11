package com.example.mainproject.presentation.ui.catalog

import android.content.Context
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
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentCatalogBinding
import com.example.mainproject.presentation.ui.customviews.ProgressContainer
import com.example.mainproject.presentation.ui.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment() {

    private lateinit var binding: FragmentCatalogBinding
    private val viewModel: CatalogViewModel by viewModels()
    private lateinit var catalogAdapter: CatalogAdapter
    private val footerAdapter = FooterCatalogAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        catalogAdapter = CatalogAdapter(parentFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateProductList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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

        viewModel.catalogUiState.observe(this as LifecycleOwner) { state ->
            when (state) {
                is CatalogUiState.ListDisplay -> {
                    binding.progressContainerCatalog.state = ProgressContainer.State.Success
                    footerAdapter.setLoading()
                }
                is CatalogUiState.Loading -> {
                    binding.progressContainerCatalog.state = ProgressContainer.State.Loading
                }
                is CatalogUiState.Error -> {
                    val currentProducts = viewModel.products.value
                    if (currentProducts == null || currentProducts.isEmpty()) {
                        binding.progressContainerCatalog.state = ProgressContainer.State.Notice(
                            getString(R.string.progress_container_error_title),
                            getString(R.string.progress_container_error_description)
                        ) {
                            viewModel.updateProductList()
                        }
                    } else {
                        footerAdapter.setError(state.throwable.message.toString()) {
                            viewModel.loadProductPage()
                        }
                    }
                }
                is CatalogUiState.FullListDisplay -> {
                    binding.progressContainerCatalog.state = ProgressContainer.State.Success
                    footerAdapter.state = FooterCatalogAdapter.FooterState.Empty
                    footerAdapter.remove()
                }
            }
        }

        viewModel.products.observe(this as LifecycleOwner) { products ->
            if (products.isEmpty()){
                binding.progressContainerCatalog.state = ProgressContainer.State.Notice(
                    getString(R.string.progress_container_empty_title),
                    getString(R.string.progress_container_empty_description)
                ) {
                    viewModel.updateProductList()
                }
            } else{
                catalogAdapter.setProducts(products)
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewCatalog.adapter = ConcatAdapter(catalogAdapter, footerAdapter)
        binding.recyclerViewCatalog.layoutManager =
            LinearLayoutManager(requireView().context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewCatalog.addItemDecoration(CatalogItemDecoration(binding.recyclerViewCatalog.context))

        val layoutManager = binding.recyclerViewCatalog.layoutManager as LinearLayoutManager
        binding.recyclerViewCatalog.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val itemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!viewModel.isLoading && footerAdapter.state == FooterCatalogAdapter.FooterState.Loading && lastVisibleItem == itemCount - 1) {
                    viewModel.loadProductPage()
                }
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(binding.recyclerViewCatalog) { view, insets ->
            val navigationBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.updatePadding(bottom = navigationBarInsets.bottom)
            insets
        }
    }
}