package com.example.mainproject.presentation.ui.product

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentProductBinding
import com.example.mainproject.presentation.ui.customviews.ProgressContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private val viewModel: ProductViewModel by viewModels()
    private val productPreviewAdapter: ProductPreviewAdapter = ProductPreviewAdapter()
    private var productDetailAdapter: ProductDetailAdapter = ProductDetailAdapter()

    private lateinit var productId: String

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

        initPreviewRecyclerView()
        initDetailRecyclerView()

        val productId = requireArguments().getString(PRODUCT_ID_ARGUMENT)
        if (productId != null) {
            this.productId = productId
            viewModel.getProduct(productId)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.constraintLayoutProduct) { view, insets ->
            val navigationBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.updatePadding(bottom = navigationBarInsets.bottom)
            insets
        }

        viewModel.productUiState.observe(this as LifecycleOwner) { state ->
            when (state) {
                is ProductUiState.Loading -> {
                    binding.progressContainerProduct.state = ProgressContainer.State.Loading
                }
                is ProductUiState.Default -> {
                    val imagesProduct = listOf(state.product.preview) + state.product.images
                    binding.viewPagerIconsProduct.adapter = ProductViewPagerAdapter(
                        requireContext(),
                        imagesProduct
                    )
                    binding.viewPagerIconsProduct.currentItem = 0
                    binding.viewPagerIconsProduct.addOnPageChangeListener(object :
                        ViewPager.OnPageChangeListener {
                        override fun onPageScrollStateChanged(state: Int) {

                        }

                        override fun onPageSelected(position: Int) {
                            productPreviewAdapter.setCurrentItemPosition(position)
                        }

                        override fun onPageScrolled(
                            position: Int,
                            positionOffset: Float,
                            positionOffsetPixels: Int
                        ) {

                        }
                    })

                    productPreviewAdapter.setImageUrls(imagesProduct)

                    productPreviewAdapter.setOnItemClickListener { position ->
                        binding.viewPagerIconsProduct.currentItem = position
                    }

                    binding.textViewCostProduct.text =
                        getString(R.string.cost_rubles, state.product.price.toString())
                    binding.textViewBadgeProduct.text = state.product.badge.value
                    binding.textViewBadgeProduct.background.setTint(Color.parseColor(state.product.badge.color))
                    binding.textViewTitleProduct.text = state.product.title
                    binding.textViewDepartmentProduct.text = state.product.department

                    val sizesProduct = state.product.sizes.filter { it.isAvailable }
                    binding.textSizeProduct.setText(sizesProduct[0].value)
                    binding.textSizeProduct.inputType = InputType.TYPE_NULL

                    binding.textSizeProduct.setOnClickListener {
                        val sizeBottomFragment = ProductSizeBottomFragment(sizesProduct)
                        sizeBottomFragment.show(parentFragmentManager, null)
                    }

                    parentFragmentManager.setFragmentResultListener(
                        ProductSizeBottomFragment.REQUEST_KEY,
                        this
                    ) { _, result ->
                        binding.textSizeProduct.setText(result.getString(ProductSizeBottomFragment.RESPONSE_KEY))
                    }

                    binding.textViewDescriptionProduct.text = state.product.description
                    productDetailAdapter.setDetails(state.product.details)

                    binding.progressContainerProduct.state = ProgressContainer.State.Success
                }
                is ProductUiState.Error -> {
                    binding.progressContainerProduct.state = ProgressContainer.State.Notice(
                        getString(R.string.progress_container_error_title),
                        getString(R.string.progress_container_error_description)
                    ) {
                        viewModel.getProduct(this.productId)
                    }
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

    private fun initDetailRecyclerView() {
        binding.recyclerViewDetailsProduct.adapter = productDetailAdapter
        binding.recyclerViewDetailsProduct.layoutManager =
            LinearLayoutManager(requireView().context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewDetailsProduct.addItemDecoration(ProductPreviewItemDecoration())
    }
}