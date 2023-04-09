package com.example.mainproject.presentation.ui.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.mainproject.R
import com.example.mainproject.databinding.ItemViewPagerProductBinding

class ProductViewPagerAdapter(
    private val context: Context,
    private val imagesURLViewPager: List<String>
) : PagerAdapter() {

    override fun getCount(): Int {
        return imagesURLViewPager.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_view_pager_product, container, false)
        val binding = ItemViewPagerProductBinding.bind(itemView)

        binding.imageViewItemViewPagerProduct.load(imagesURLViewPager[position]) {
            placeholder(R.drawable.ic_logo)
            error(R.drawable.ic_logo)
            transformations(RoundedCornersTransformation(context.resources.getDimension(R.dimen.image_view_pager_product_border_radius)))
        }

        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}