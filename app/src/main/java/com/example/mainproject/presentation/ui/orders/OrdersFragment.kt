package com.example.mainproject.presentation.ui.orders

import android.os.Bundle
import android.os.Handler
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
    private val viewModel: OrderViewModel by viewModels()
    private lateinit var viewPagerAdapter: OrderViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        viewPagerAdapter = OrderViewPagerAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarOrders.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.viewPager2Orders.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayoutOrders, binding.viewPager2Orders) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.text_tab_all)
                1 -> tab.text = resources.getString(R.string.text_tab_active)
            }
        }.attach()

        viewModel.ordersUiState.observe(this as LifecycleOwner) { state ->
            when (state) {
                is OrdersUiState.Loading -> {
                    binding.progressContainerOrders.state = ProgressContainer.State.Loading
                }
                is OrdersUiState.Default -> {
                    binding.progressContainerOrders.state = ProgressContainer.State.Success
                }
                is OrdersUiState.Empty -> {
                    binding.progressContainerOrders.state =
                        ProgressContainer.State.Notice(
                            getString(R.string.progress_container_empty_title),
                            getString(R.string.progress_container_empty_description)
                        ) {

                        }
                }
                is OrdersUiState.Error -> {
                    binding.progressContainerOrders.state =
                        ProgressContainer.State.Notice(
                            getString(R.string.progress_container_error_title),
                            getString(R.string.progress_container_error_description)
                        ) {

                        }
                }
            }
        }
    }
}