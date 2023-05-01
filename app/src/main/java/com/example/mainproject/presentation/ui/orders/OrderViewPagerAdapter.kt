package com.example.mainproject.presentation.ui.orders

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OrderViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var items = listOf<OrdersItemViewPager2Fragment>()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }

    fun setItems(items: List<OrdersItemViewPager2Fragment>) {
        this.items = items
    }

    fun getItems(): List<OrdersItemViewPager2Fragment> {
        return items
    }
}