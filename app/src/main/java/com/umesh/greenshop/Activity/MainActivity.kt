package com.umesh.greenshop.Activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.umesh.greenshop.Adapter.CategoryAdapter
import com.umesh.greenshop.Adapter.RecommendationAdapter
import com.umesh.greenshop.Adapter.SliderAdapter
import com.umesh.greenshop.Model.SliderModel
import com.umesh.greenshop.ViewModel.MainViewModel
import com.umesh.greenshop.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private val viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initBanners()
        initCategory()
        initRecommended()
    }

    private fun initBanners() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banner.observe(this) {
            banner(it)
            binding.progressBarBanner.visibility = View.GONE

        }

        viewModel.loadBanner()
    }

    private fun banner(it: List<SliderModel>) {
        binding.viewPagerSlider.adapter = SliderAdapter(it, binding.viewPagerSlider)
        binding.viewPagerSlider.clipToPadding = false
        binding.viewPagerSlider.clipChildren = false
        binding.viewPagerSlider.offscreenPageLimit = 3
        binding.viewPagerSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val composePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }

        binding.viewPagerSlider.setPageTransformer(composePageTransformer)

        if (it.size > 1) {
            binding.dotsIndicator.visibility = View.VISIBLE
            binding.dotsIndicator.attachTo(binding.viewPagerSlider)
        }


    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.category.observe(this) {
            binding.viewCategory.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL, false
            )
            binding.viewCategory.adapter = CategoryAdapter(it)
            binding.progressBarCategory.visibility = View.GONE

        }
        viewModel.loadCategory()
    }

    private fun initRecommended() {
        binding.progressBarRecommendation.visibility = View.VISIBLE
        viewModel.recommend.observe(this) {
            binding.viewRecommendation.layoutManager = GridLayoutManager(
                this@MainActivity,
                2
            )
            binding.viewRecommendation.adapter = RecommendationAdapter(it)
            binding.progressBarRecommendation.visibility = View.GONE
        }
        viewModel.loadRecommended()
    }
}