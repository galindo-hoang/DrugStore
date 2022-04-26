package com.example.drugstore.presentation.admin.order

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.databinding.FragmentOrderAdminBinding
import com.example.drugstore.presentation.adapter.OrderAdapter
import com.example.drugstore.presentation.order.OrderVM
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderAdminFragment : Fragment() {
    private lateinit var binding: FragmentOrderAdminBinding
    @Inject
    lateinit var orderVM: OrderVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderAdminBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment

        val orderAdapter = OrderAdapter()
        orderVM.getAllOrder().observe(viewLifecycleOwner){
            if (it != null) {
                orderAdapter.setList(it)
            }
        }
        orderAdapter.onItemClick = {order ->
            val intent = Intent(context,OrderStatusAdminActivity::class.java)
            intent.putExtra(Constants.ORDER_ID,order.OrderID)
            startActivity(intent)
        }
        binding.rcViewOrderHistory.adapter = orderAdapter
        binding.rcViewOrderHistory.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
}