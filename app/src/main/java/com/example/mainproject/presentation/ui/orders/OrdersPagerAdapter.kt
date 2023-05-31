package com.example.mainproject.presentation.ui.orders

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OrdersPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var items = listOf<OrdersListFragment>()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }

    fun setItems(items: List<OrdersListFragment>) {
        this.items = items
    }

    fun getItems(): List<OrdersListFragment> {
        return items
    }
}