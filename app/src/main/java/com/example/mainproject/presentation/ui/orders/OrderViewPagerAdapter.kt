package com.example.mainproject.presentation.ui.orders

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OrderViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return OrdersItemViewPager2Fragment.newInstance(
            if (position == 1) OrdersItemViewPager2Fragment.ACTIVE_TYPE
            else OrdersItemViewPager2Fragment.ALL_TYPE
        )
    }
}