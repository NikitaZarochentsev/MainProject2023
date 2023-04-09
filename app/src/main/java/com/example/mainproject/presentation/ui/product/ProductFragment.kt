package com.example.mainproject.presentation.ui.product

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainproject.databinding.FragmentProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var productPreviewAdapter: ProductPreviewAdapter

    companion object {
        private const val PRODUCT_ID_ARGUMENT = "PRODUCT_ID_ARGUMENT"

        fun newInstance(productId: String): ProductFragment {
            val arguments = Bundle()
            arguments.putString(PRODUCT_ID_ARGUMENT, productId)
            val fragment = ProductFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        productPreviewAdapter = ProductPreviewAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarProduct.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // не забыть удалить
        val images = listOf(
            "https://images.geminipremium.com/2021/10/nike-the-champions-tampa-bay-buccaneers-super-bowl-lv-t-shirt-Shirt.jpg",
            "https://images.geminipremium.com/2021/10/nike-the-champions-tampa-bay-buccaneers-super-bowl-lv-t-shirt-Shirt.jpg",
            "https://images.geminipremium.com/2021/10/nike-the-champions-tampa-bay-buccaneers-super-bowl-lv-t-shirt-Shirt.jpg"
        )

        binding.viewPagerIconsProduct.adapter = ProductViewPagerAdapter(requireContext(), images)
        binding.viewPagerIconsProduct.currentItem = 0

        initPreviewRecyclerView()
        productPreviewAdapter.setImageUrls(images)

        viewModel.productUiState.observe(this as LifecycleOwner) { state ->
            when (state) {
                is ProductUiState.Loading -> {
                    // сделать отображение загрузки
                }
                is ProductUiState.Default -> {
                    // отображение продукта
                }
                is ProductUiState.Error -> {
                    // отображение ошибки
                }
            }
        }
    }

    private fun initPreviewRecyclerView() {
        binding.recyclerViewPreviewProduct.adapter = productPreviewAdapter
        binding.recyclerViewPreviewProduct.layoutManager =
            LinearLayoutManager(requireView().context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewPreviewProduct.addItemDecoration(ProductPreviewItemDecoration())
    }
}