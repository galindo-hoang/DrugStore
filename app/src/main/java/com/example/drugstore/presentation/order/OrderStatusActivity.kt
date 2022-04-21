package com.example.drugstore.presentation.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.R
import com.example.drugstore.databinding.ActivityOrderStatusBinding
import com.example.drugstore.presentation.adapter.OrderProductAdapter
import com.example.drugstore.presentation.home.HomeActivity
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class OrderStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderStatusBinding
    private var orderID:String? = null
    @Inject
    lateinit var orderVM: OrderVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()

        if(intent.hasExtra(Constants.ORDER_ID)){
            orderID = intent.getStringExtra(Constants.ORDER_ID)
        }


        val orderProductAdapter = OrderProductAdapter()

        orderID?.let { ID ->
            orderVM.setOrderByID(ID).observe(this){
                if (it != null) {
                    binding.tvPhoneNumber.text = it.Address.phoneNumber
                    binding.tvAddress.text = it.Address.address
                    binding.tvOrderID.text = it.OrderID
                    binding.tvPayment.text = when(it.PaymentID){
                        0 -> "Tiền mặt"
                        1 -> "Visa"
                        2 -> "Paypal"
                        else -> "Apple Pay"
                    }
                    var sumPrice = 0
                    it.ProductList.forEach { i ->
                        sumPrice += i.Price*i.Quantity
                    }

                    binding.tvOrderStatus.text = when(it.Status){
                        true -> "Giao thành công"
                        else -> "Đang vận chuyển"
                    }
                    binding.tvSumPrice.text = "${sumPrice.toString()} VND"
                    orderProductAdapter.setItems(it.ProductList)
                }
            }
        }

        binding.rvProduct.adapter = orderProductAdapter
        binding.rvProduct.layoutManager = LinearLayoutManager(this)
    }

    private fun setToolBar() {
        setSupportActionBar(binding.tb)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.tb.title = ""
        binding.tb.setNavigationOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finishAffinity()
        }
    }
}