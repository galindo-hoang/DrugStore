package com.example.drugstore.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.databinding.FragmentCartBinding
import com.example.drugstore.presentation.adapter.CartAdapter
import com.example.drugstore.presentation.order.OrderActivity
import com.example.drugstore.presentation.order.OrderFragment
import java.text.DecimalFormat
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private var cartVM:CartVM? = null
    private var sum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartVM = ViewModelProvider(this,CartVM.CartProductVMFactory(requireActivity().application))[CartVM::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater,container,false)

        val cartAdapter = CartAdapter()
        cartVM!!.getCartProducts().observe(viewLifecycleOwner){
            cartAdapter.setList(it)
            sum = 0
            it.forEach { i ->
                sum += i.Quantity*i.Price
            }
            binding.tvRawPrice.text = "${DecimalFormat("##,###").format(sum)}"
            binding.tvSumPrice.text = "${DecimalFormat("##,###").format(sum)}"
        }
        binding.rcViewCart.adapter = cartAdapter
        binding.rcViewCart.layoutManager = LinearLayoutManager(context)
        // Inflate the layout for this fragment

        binding.btnContinue.setOnClickListener {
            if(sum == 0) {
                Toast.makeText(context,"Please chose product to continue to order",Toast.LENGTH_SHORT).show()
            }else startActivity(Intent(context,OrderActivity::class.java))
        }
        return binding.root
    }
}