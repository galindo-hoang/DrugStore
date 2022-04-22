package com.example.drugstore.presentation.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.R
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.data.models.Address
import com.example.drugstore.data.models.CartProduct
import com.example.drugstore.data.models.Order
import com.example.drugstore.data.models.OrderProduct
import com.example.drugstore.databinding.ActivityOrderBinding
import com.example.drugstore.presentation.adapter.AddressAdapter
import com.example.drugstore.presentation.home.AddressVM
import com.example.drugstore.presentation.home.CartVM
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class OrderActivity : AppCompatActivity() {
    private var sum:Int = 0
    private var listProduct: ArrayList<CartProduct> = ArrayList()
    private var addressVM: AddressVM? = null
    private var currentAddress: Address? = null
    private var cartProductVM: CartVM? = null
    @Inject
    lateinit var orderVM: OrderVM
    private lateinit var binding:ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addressVM = ViewModelProvider(this, AddressVM.AddressVMFactory(application))[AddressVM::class.java]
        cartProductVM = ViewModelProvider(this,CartVM.CartProductVMFactory(application))[CartVM::class.java]

        val addressAdapter = AddressAdapter()
        addressAdapter.onClickRadioListener = {address -> currentAddress = address }
        addressVM!!.getListAddresses().observe(this){
            addressAdapter.setList(it)
        }
        binding.rvAddress.adapter = addressAdapter
        binding.rvAddress.layoutManager = LinearLayoutManager(this)


        cartProductVM!!.getCartProducts().observe(this){
            listProduct.clear()
            listProduct = it as ArrayList<CartProduct>
//            sum = 0
//            it.forEach { i ->
//                listProduct.add(i)
//                sum += i.Quantity*i.Price
//            }
//            binding.tvPrice.text = "${DecimalFormat("##,###").format(sum)}"
        }

        binding.btnOrder.setOnClickListener {
            setUpOrder()
        }

        binding.tvAddAddress.setOnClickListener {
            startActivity(Intent(this,AddPlaceActivity::class.java))
        }
    }



    private fun setUpOrder() {
        val payment = when{
            binding.rbCash.isChecked -> 0
            binding.rbVisa.isChecked -> 1
            binding.rbPaypal.isChecked -> 2
            else -> 3
        }

        if(currentAddress == null){
            Toast.makeText(this,"Please chose address to ship",Toast.LENGTH_SHORT).show()
        }else{
            val order = Order(UserID = FirebaseClass.getCurrentUserId(), Address = currentAddress!!, Point = 0, PaymentID = payment, ProductList = listProduct)
            val intent = Intent(this,OrderStatusActivity::class.java)
            orderVM.insertOrder(order).observe(this){
                intent.putExtra(Constants.ORDER_ID,it.toString())
                startActivity(intent)
                cartProductVM!!.deleteAll()
            }
        }
    }
}