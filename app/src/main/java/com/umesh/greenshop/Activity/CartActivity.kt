package com.umesh.greenshop.Activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.umesh.greenshop.Adapter.CartAdapter
import com.umesh.greenshop.Helper.ChangeNumberItemsListener
import com.umesh.greenshop.Helper.ManagmentCart
import com.umesh.greenshop.databinding.ActivityCartBinding

class CartActivity : BaseActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var managmentCart: ManagmentCart
    private var tax: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)
        setVariable()
        calculateCart()
        initializeCart()

    }

    private fun initializeCart() {
        with(binding) {
            emptyText.visibility =
                if (managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView3.visibility =
                if (managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }

        binding.viewCart.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.viewCart.adapter =
            CartAdapter(managmentCart.getListCart(), this, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calculateCart()
                }

            })
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 10
        tax = Math.round((managmentCart.getTotalFee() * percentTax) * 100) / 100.0
        val total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100
        val itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100

        with(binding) {
            totalFeeText.text = "$" + itemTotal.toString()
            deliveryText.text = "$" + delivery.toString()
            taxText.text = "$" + tax.toString()
            totalText.text = "$" + total.toString()

        }
    }

    private fun setVariable() {
        binding.backButton.setOnClickListener { finish() }

    }
}