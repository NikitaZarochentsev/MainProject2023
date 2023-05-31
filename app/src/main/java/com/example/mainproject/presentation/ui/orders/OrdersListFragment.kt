package com.example.mainproject.presentation.ui.orders

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentOrdersListBinding
import com.example.mainproject.presentation.ui.catalog.productslist.ProductsListItemDecoration
import com.example.mainproject.presentation.ui.customviews.ProgressContainer
import com.example.mainproject.presentation.ui.orders.orderslist.FooterOrdersListAdapter
import com.example.mainproject.presentation.ui.orders.orderslist.OrdersListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersListFragment : Fragment() {

    private lateinit var binding: FragmentOrdersListBinding
    private val viewModel: OrdersViewModel by viewModels(ownerProducer = { this.requireParentFragment() })

    private val ordersListAdapter = OrdersListAdapter()
    private val footerAdapter = FooterOrdersListAdapter()
    private var type = ALL_TYPE

    companion object {
        const val ALL_TYPE = "TYPE_ALL"
        const val ACTIVE_TYPE = "TYPE_ACTIVE"
        private const val TYPE_ARGUMENT = "TYPE_ARGUMENT"

        fun newInstance(type: String): OrdersListFragment {
            val arguments = Bundle()
            arguments.putString(TYPE_ARGUMENT, type)
            val fragment = OrdersListFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        type = requireArguments().getString(TYPE_ARGUMENT, ALL_TYPE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOrdersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        viewModel.ordersUiState.observe(this as LifecycleOwner) { state ->
            when (state) {
                is OrdersUiState.ListDisplay -> {
                    when (type) {
                        ALL_TYPE -> {
                            binding.progressContainerItemViewPager2Orders.state =
                                ProgressContainer.State.Success
                        }
                        ACTIVE_TYPE -> {
                            if (state.activeIsEmpty) {
                                binding.progressContainerItemViewPager2Orders.state =
                                    ProgressContainer.State.Notice(
                                        getString(R.string.progress_container_empty_title),
                                        getString(R.string.progress_container_empty_description)
                                    ) {
                                        viewModel.updateOrderList()
                                    }
                            } else {
                                binding.progressContainerItemViewPager2Orders.state =
                                    ProgressContainer.State.Success
                            }
                        }
                    }

                }
                is OrdersUiState.FullListDisplay -> {
                    when (type) {
                        ALL_TYPE -> {
                            binding.progressContainerItemViewPager2Orders.state =
                                ProgressContainer.State.Success
                            if (footerAdapter.state != FooterOrdersListAdapter.FooterState.Empty) {
                                footerAdapter.setEmpty()
                            }
                        }
                        ACTIVE_TYPE -> {
                            if (state.activeIsEmpty) {
                                binding.progressContainerItemViewPager2Orders.state =
                                    ProgressContainer.State.Notice(
                                        getString(R.string.progress_container_empty_title),
                                        getString(R.string.progress_container_empty_description)
                                    ) {
                                        viewModel.updateOrderList()
                                    }
                            } else {
                                binding.progressContainerItemViewPager2Orders.state =
                                    ProgressContainer.State.Success
                                if (footerAdapter.state != FooterOrdersListAdapter.FooterState.Empty) {
                                    footerAdapter.setEmpty()
                                }
                            }
                        }
                    }

                }
                is OrdersUiState.Error -> {
                    val currentOrders = viewModel.ordersAll.value
                    if (currentOrders != null && currentOrders.isNotEmpty()) {
                        footerAdapter.setError(state.throwable.message.toString()) {
                            viewModel.updateOrderList()
                        }
                    }
                }
                is OrdersUiState.Empty -> {
                    binding.progressContainerItemViewPager2Orders.state =
                        ProgressContainer.State.Notice(
                            getString(R.string.progress_container_empty_title),
                            getString(R.string.progress_container_empty_description)
                        ) {
                            viewModel.updateOrderList()
                        }
                }
                else -> {
                    // ничего делать не нужно, за состояния загрузки и ошибки отвечает
                }
            }
        }

        when (type) {
            ALL_TYPE -> {
                viewModel.ordersAll.observe(this as LifecycleOwner) { orders ->
                    ordersListAdapter.setOrders(orders)
                    ordersListAdapter.setOnItemClickListener { orderId ->
                        viewModel.cancelOrder(orderId)
                    }
                }
            }
            ACTIVE_TYPE -> {
                viewModel.ordersActive.observe(this as LifecycleOwner) { orders ->
                    ordersListAdapter.setOrders(orders)
                    ordersListAdapter.setOnItemClickListener { orderId ->
                        viewModel.cancelOrder(orderId)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewItemViewPager2Orders.adapter =
            ConcatAdapter(ordersListAdapter, footerAdapter)
        binding.recyclerViewItemViewPager2Orders.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewItemViewPager2Orders.addItemDecoration(
            ProductsListItemDecoration(
                binding.recyclerViewItemViewPager2Orders.context
            )
        )

        val layoutManager =
            binding.recyclerViewItemViewPager2Orders.layoutManager as LinearLayoutManager
        binding.recyclerViewItemViewPager2Orders.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val itemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!viewModel.isLoading && footerAdapter.state == FooterOrdersListAdapter.FooterState.Loading && lastVisibleItem == itemCount - 1) {
                    viewModel.loadOrderPage()
                }
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(binding.recyclerViewItemViewPager2Orders) { view, insets ->
            val navigationBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.updatePadding(bottom = navigationBarInsets.bottom)
            insets
        }
    }
}