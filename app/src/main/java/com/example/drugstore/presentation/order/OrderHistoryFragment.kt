package com.example.drugstore.presentation.order

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.databinding.FragmentOrderHistoryBinding
import com.example.drugstore.presentation.adapter.OrderAdapter
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderHistoryFragment : Fragment() {
    private lateinit var binding: FragmentOrderHistoryBinding
    @Inject
    lateinit var orderVM: OrderVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderHistoryBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment

        val orderAdapter = OrderAdapter()
        orderVM.getOrderByUser(true).observe(viewLifecycleOwner){
            it?.let { it1 -> orderAdapter.setList(it1) }
        }

        orderAdapter.onItemClick = {order ->
            val intent = Intent(context,OrderStatusActivity::class.java)
            intent.putExtra(Constants.ORDER_ID,order.OrderID)
            startActivity(intent)
        }

        binding.rcViewOrderHistory.adapter = orderAdapter
        binding.rcViewOrderHistory.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
}