package com.umesh.greenshop.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.umesh.greenshop.Adapter.ColorAdapter
import com.umesh.greenshop.Adapter.SliderAdapter
import com.umesh.greenshop.Helper.ManagmentCart
import com.umesh.greenshop.Model.ItemsModel
import com.umesh.greenshop.Model.SliderModel
import com.umesh.greenshop.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOrder = 1
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)
        getBundle()
        banners()
        initColorList()
    }

    private fun initColorList() {
        val colorList: ArrayList<String> = ArrayList()
        for (imageUrl in item.picUrl) {
            colorList.add(imageUrl)
        }
        binding.colorlist.adapter = ColorAdapter(colorList)
        binding.colorlist.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun banners() {
        val sliderItems: ArrayList<SliderModel> = ArrayList()
        for (imageUrl in item.picUrl) {
            sliderItems.add(SliderModel(imageUrl))
        }

        binding.slider.adapter = SliderAdapter(sliderItems, binding.slider)
        binding.slider.clipToPadding = false
        binding.slider.clipChildren = false
        binding.slider.offscreenPageLimit = 1
        binding.slider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val composePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }

        binding.slider.setPageTransformer(composePageTransformer)

        if (sliderItems.size > 1) {
            binding.dotsIndicator.visibility = View.VISIBLE
            binding.dotsIndicator.attachTo(binding.slider)
        }

    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!
        binding.titleText.text = item.title
        binding.descriptionText.text = item.description
        binding.priceText.text = "$ " + item.price.toString()
        binding.ratingText.text = "${item.rating} Rating"
        binding.addToCart.setOnClickListener {
            item.numberInCart = numberOrder
            managmentCart.insertItem(item)
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()

        }
        binding.backButton.setOnClickListener { finish() }
        binding.cartButton.setOnClickListener {
            startActivity(Intent(this@DetailActivity, CartActivity::class.java))
        }
    }
}