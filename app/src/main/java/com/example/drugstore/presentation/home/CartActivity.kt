package com.example.drugstore.presentation.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.databinding.ActivityCartBinding
import com.example.drugstore.presentation.adapter.CartAdapter
import com.example.drugstore.presentation.order.OrderActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    @Inject
    lateinit var cartVM:CartVM

    private var sum = 0
    private val cartAdapter = CartAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBindToolbar()
        onBindView()
        onBindEvent()
    }

    private fun onBindToolbar() {
        binding.tb.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun onBindView() {
        cartVM.getCartProducts().observe(this){
            cartAdapter.setList(it)
            sum = 0
            it.forEach { i ->
                sum += i.Quantity*i.Price
            }
            binding.tvRawPrice.text = DecimalFormat("##,###").format(sum)
            binding.tvSumPrice.text = DecimalFormat("##,###").format(sum)
        }
        binding.rcViewCart.adapter = cartAdapter
        binding.rcViewCart.layoutManager = LinearLayoutManager(this)
    }

    private fun onBindEvent() {
        cartAdapter.onMinusClick = {cartProduct ->
            cartVM.decreaseQuantityProduct(cartProduct.Quantity,cartProduct.ProID)
        }

        cartAdapter.onAddClick = {cartProduct ->
            cartVM.increaseQuantityProduct(cartProduct.Quantity,cartProduct.ProID)
        }

        binding.btnContinue.setOnClickListener {
            if(sum == 0) {
                Toast.makeText(this,"Please chose product to continue to order", Toast.LENGTH_SHORT).show()
            }else startActivity(Intent(this, OrderActivity::class.java))
        }
    }


}