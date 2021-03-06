package com.example.drugstore.presentation.order

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.databinding.ActivityOrderStatusBinding
import com.example.drugstore.presentation.adapter.OrderProductAdapter
import com.example.drugstore.presentation.chat.ChatActivity
import com.example.drugstore.presentation.home.HomeActivity
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import javax.inject.Inject
@AndroidEntryPoint
class OrderStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderStatusBinding
    private var orderID:String? = null
    @Inject
    lateinit var orderVM: OrderVM
    @SuppressLint("SetTextI18n")
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
            orderVM.getOrderByID(ID).observe(this){
                if (it != null) {
                    binding.etPhoneNumber.text = it.Address.phoneNumber
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

        onBindEvent()
    }

    private fun onBindEvent() {
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
            startActivity(Intent(this,HomeActivity::class.java))
            finishAffinity()
        }
    }
}