package com.example.drugstore.presentation.admin.order

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.databinding.ActivityOrderStatusAdminBinding
import com.example.drugstore.presentation.adapter.OrderProductAdapter
import com.example.drugstore.presentation.chat.ChatActivity
import com.example.drugstore.presentation.home.HomeActivity
import com.example.drugstore.presentation.order.OrderVM
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class OrderStatusAdminActivity : AppCompatActivity() {
    private var orderID: String? = null
    private val orderProductAdapter = OrderProductAdapter()
    private lateinit var binding: ActivityOrderStatusAdminBinding
    @Inject lateinit var  orderVM: OrderVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderStatusAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()
        if(intent.hasExtra(Constants.ORDER_ID)){
            orderID = intent.getStringExtra(Constants.ORDER_ID)
        }
        onBindView()
        onBindEvent()
    }

    @SuppressLint("SetTextI18n")
    private fun onBindView() {
        orderID?.let { id ->
            orderVM.getOrderByID(id).observe(this){
                Log.e("======",it.toString())
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
                    binding.tvSumPrice.text = "${DecimalFormat("##,###").format(sumPrice)} VND"
                    orderProductAdapter.setItems(it.ProductList)
                }
            }
        }
        binding.rvProduct.adapter = orderProductAdapter
        binding.rvProduct.layoutManager = LinearLayoutManager(this)
        orderVM.getStatusOrder.observe(this){
            if(it) {
                binding.llStatus.visibility = View.INVISIBLE
                binding.tvOrderStatus.text = "Giao thành công"
            }
        }
    }

    private fun onBindEvent() {
        binding.btnAccept.setOnClickListener {
            orderVM.acceptOrder(orderID)
        }
        binding.btnChat.setOnClickListener {
            val intent = Intent(this,ChatActivity::class.java)
            intent.putExtra(Constants.ORDER_ID,orderID)
            startActivity(intent)
        }
    }

    private fun setToolBar() {
        setSupportActionBar(binding.tb)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.tb.title = ""
        binding.tb.setNavigationOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }
    }
}