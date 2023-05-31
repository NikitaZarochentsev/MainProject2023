package com.example.mainproject.presentation.ui.orders

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentOrdersBinding
import com.example.mainproject.presentation.ui.customviews.ProgressContainer
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding
    private val viewModel: OrdersViewModel by viewModels()
    private lateinit var viewPagerAdapter: OrdersPagerAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewPagerAdapter = OrdersPagerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateOrderList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarOrders.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        initViewPager()

        viewModel.ordersUiState.observe(this as LifecycleOwner) { state ->
            when (state) {
                is OrdersUiState.Loading -> {
                    binding.progressContainerOrders.state = ProgressContainer.State.Loading
                }
                is OrdersUiState.ListDisplay -> {
                    binding.progressContainerOrders.state = ProgressContainer.State.Success
                }
                is OrdersUiState.Error -> {
                    binding.progressContainerOrders.state =
                        ProgressContainer.State.Notice(
                            getString(R.string.progress_container_error_title),
                            getString(R.string.progress_container_error_description)
                        ) {
                            viewModel.updateOrderList()
                        }
                }
                is OrdersUiState.FullListDisplay -> {
                    binding.progressContainerOrders.state = ProgressContainer.State.Success
                }
                is OrdersUiState.Empty -> {
                    binding.progressContainerOrders.state =
                        ProgressContainer.State.Notice(
                            getString(R.string.progress_container_empty_title),
                            getString(R.string.progress_container_empty_description)
                        ) {
                            viewModel.updateOrderList()
                        }
                }
            }
        }

        viewModel.ordersAll.observe(this as LifecycleOwner) { orders ->
            binding.tabLayoutOrders.getTabAt(0)?.text =
                resources.getString(R.string.text_tab_all, orders.size)
        }

        viewModel.ordersActive.observe(this as LifecycleOwner) { orders ->
            binding.tabLayoutOrders.getTabAt(1)?.text =
                resources.getString(R.string.text_tab_active, orders.size)
        }
    }

    private fun initViewPager() {
        binding.viewPager2Orders.offscreenPageLimit = 1
        binding.viewPager2Orders.adapter = viewPagerAdapter
        val items = mutableListOf(
            OrdersListFragment.newInstance(OrdersListFragment.ALL_TYPE),
            OrdersListFragment.newInstance(OrdersListFragment.ACTIVE_TYPE)
        )
        viewPagerAdapter.setItems(items)

        TabLayoutMediator(binding.tabLayoutOrders, binding.viewPager2Orders) { tab, position ->
            when (position) {
                0 -> tab.text =
                    resources.getString(
                        R.string.text_tab_all, 0
                    )
                1 -> tab.text =
                    resources.getString(
                        R.string.text_tab_active, 0
                    )
            }
        }.attach()
    }
}