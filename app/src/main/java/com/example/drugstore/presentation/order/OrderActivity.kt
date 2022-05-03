package com.example.drugstore.presentation.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.R
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.data.models.Address
import com.example.drugstore.data.models.CartProduct
import com.example.drugstore.data.models.Order
import com.example.drugstore.databinding.ActivityOrderBinding
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.adapter.AddressAdapter
import com.example.drugstore.presentation.home.CartVM
import com.example.drugstore.presentation.user.ProfileVM
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderActivity : BaseActivity() {
    private lateinit var loadAddress: ActivityResultLauncher<Intent>
    private var sum:Int = 0
    private var listProduct: ArrayList<CartProduct> = ArrayList()
    private var currentAddress: Address? = null
    private val addressAdapter = AddressAdapter()
    @Inject lateinit var cartVM: CartVM
    @Inject lateinit var orderVM: OrderVM
    @Inject lateinit var profileVM: ProfileVM

    private lateinit var binding:ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLoadDataFromOtherActivities()
        onBindView()
        onBindEvent()
    }

    private fun onBindEvent() {
        addressAdapter.onClickRadioListener = {address -> currentAddress = address }

        binding.tvAddAddress.setOnClickListener {
            loadAddress.launch(Intent(this,AddPlaceActivity::class.java))
        }

        binding.btnOrder.setOnClickListener { setUpOrder() }
    }



    private fun onBindView() {
        profileVM.setupListAddress()
        profileVM.getListAddress.observe(this){
            addressAdapter.setList(it)
        }
        binding.rvAddress.adapter = addressAdapter
        binding.rvAddress.layoutManager = LinearLayoutManager(this)

        cartVM.getCartProducts().observe(this){
            listProduct.clear()
            listProduct = it as ArrayList<CartProduct>
        }
    }




    private fun setUpOrder() {
        this.showProgressDialog(resources.getString(R.string.please_wait))
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
                this.hideProgressDialog()
                if(it != ""){
                    intent.putExtra(Constants.ORDER_ID,it.toString())
                    startActivity(intent)
                    cartVM.deleteAll()
                }else{
                    Toast.makeText(this,"Cant order products",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun setupLoadDataFromOtherActivities(){
        loadAddress =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    if(result.data?.getBooleanExtra(Constants.ADDRESS,false) == true) {
                        profileVM.setupListAddress()
                    }
                }
            }
    }
}