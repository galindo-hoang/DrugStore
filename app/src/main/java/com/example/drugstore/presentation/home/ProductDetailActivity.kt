package com.example.drugstore.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.data.models.CartProduct
import com.example.drugstore.data.models.Product
import com.example.drugstore.databinding.ActivityProductDetailBinding
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.adapter.NutritionAdapter
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailActivity : BaseActivity() {
    private var cartProduct: CartProduct? = null
    private var product: Product = Product()
    private var productID: Int = -1
    private var maxQuantity = true
    private lateinit var binding: ActivityProductDetailBinding

    @Inject
    lateinit var categoryVM: CategoryVM
    @Inject
    lateinit var cartVM: CartVM
    @Inject
    lateinit var productVM: ProductVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.hasExtra(Constants.PRODUCT_ID)){
            productID = intent.getIntExtra(Constants.PRODUCT_ID,-1)
        }
        productVM.getProductByID(productID)
        productVM.product.observe(this){
            product = it
            onBindToolbar()
            setUpView()
            setUpEvent()
        }
    }


    private fun onBindToolbar() {
        binding.tb.setNavigationOnClickListener {
            onBackPressed()
        }
    }


    private fun setUpEvent() {
        binding.btnMinus.setOnClickListener {
            if(cartProduct != null) cartVM.decreaseQuantityProduct(cartProduct!!.Quantity,product.ProID)
        }

        binding.btnAdd.setOnClickListener {
            if(!maxQuantity){
                if(cartProduct == null) cartVM.insertProduct(product)
                else cartVM.increaseQuantityProduct(cartProduct!!.Quantity,product.ProID)
            }else Toast.makeText(this,"Product out of stock",Toast.LENGTH_SHORT).show()
        }

        binding.btnCartTop.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpView() {
        Glide.with(this)
            .load(product.ProImage)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.ivPro)
        categoryVM.getCategory(product.CatID).observe(this){
            if (it != null) {
                binding.tvCatName.text = it.CatName
            }
        }
        binding.tvQuantity.text = "Quantity: ${product.Quantity}"
        binding.tvDes.text = product.Description
        binding.tvProName.text = product.ProName


        binding.rcProductDetail.adapter = NutritionAdapter(product.NutritionList)
        binding.rcProductDetail.layoutManager = LinearLayoutManager(this)

        cartVM.getQuantityProducts().observe(this){
            if(it > 0){
                binding.tvQuantityProduct.visibility = View.VISIBLE
                binding.tvQuantityProduct.text = it.toString()
            }else{
                binding.tvQuantityProduct.visibility = View.INVISIBLE
            }
        }

        cartVM.getProductById(product.ProID).observe(this){
            cartProduct = it
            if(it != null){
                maxQuantity = it.Quantity >= product.Quantity
                binding.tvProductQuantityInCart.text = it.Quantity.toString()
                binding.tvSumPrice.text = DecimalFormat("##,###").format(it.Quantity*product.Price.toDouble())
            }else{
                maxQuantity = product.Quantity == 0
                binding.tvProductQuantityInCart.text = "0"
                binding.tvSumPrice.text = DecimalFormat("##,###").format(product.Price.toDouble())
            }
        }
    }
}