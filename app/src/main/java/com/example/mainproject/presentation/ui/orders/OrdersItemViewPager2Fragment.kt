package com.example.mainproject.presentation.ui.orders

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentItemViewPager2OrdersBinding
import com.example.mainproject.presentation.ui.customviews.ProgressContainer
import com.example.mainproject.presentation.ui.product.ProductPreviewItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersItemViewPager2Fragment : Fragment() {

    private lateinit var binding: FragmentItemViewPager2OrdersBinding
    private val viewModel: OrdersItemViewModel by viewModels()
    private lateinit var orderAdapter: OrderAdapter
    private var type = ALL_TYPE
    private lateinit var onSizeChange: (sizeCurrent: Int) -> Unit

    companion object {
        const val ALL_TYPE = "TYPE_ALL"
        const val ACTIVE_TYPE = "TYPE_ACTIVE"
        private const val TYPE_ARGUMENT = "TYPE_ARGUMENT"

        fun newInstance(type: String): OrdersItemViewPager2Fragment {
            val arguments = Bundle()
            arguments.putString(TYPE_ARGUMENT, type)
            val fragment = OrdersItemViewPager2Fragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateOrdersList(type)
    }

    fun initAdapter() {
        type = requireArguments().getString(TYPE_ARGUMENT, ALL_TYPE)
        orderAdapter = OrderAdapter()
        orderAdapter.setTypeOrders(type)
    }

    fun setSizeChangeListener(onSizeChange: (size: Int) -> Unit) {
        this.onSizeChange = onSizeChange
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemViewPager2OrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateOrdersList(type)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewItemViewPager2Orders.adapter = orderAdapter
        binding.recyclerViewItemViewPager2Orders.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewItemViewPager2Orders.addItemDecoration(ProductPreviewItemDecoration())

        viewModel.ordersItemUiState.observe(this as LifecycleOwner) { state ->
            when (state) {
                is OrdersItemUiState.Default -> {
                    binding.progressContainerItemViewPager2Orders.state =
                        ProgressContainer.State.Success
                    orderAdapter.setOrders(state.orders)
                    orderAdapter.setOnItemClickListener { orderId ->
                        viewModel.cancelOrder(orderId, type)
                    }
                    onSizeChange(state.orders.size)
                }
                is OrdersItemUiState.Loading -> {
                    binding.progressContainerItemViewPager2Orders.state =
                        ProgressContainer.State.Loading
                }
                is OrdersItemUiState.Empty -> {
                    binding.progressContainerItemViewPager2Orders.state =
                        ProgressContainer.State.Notice(
                            getString(R.string.progress_container_empty_title),
                            getString(R.string.progress_container_empty_description)
                        ) {
                            viewModel.updateOrdersList(type)
                        }
                }
                is OrdersItemUiState.Error -> {
                    binding.progressContainerItemViewPager2Orders.state =
                        ProgressContainer.State.Notice(
                            getString(R.string.progress_container_error_title),
                            getString(R.string.progress_container_error_description)
                        ) {
                            viewModel.updateOrdersList(type)
                        }
                }
            }
        }
    }
}