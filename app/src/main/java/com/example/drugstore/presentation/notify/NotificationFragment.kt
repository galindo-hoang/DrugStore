package com.example.drugstore.presentation.notify

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.databinding.FragmentNotificationBinding
import com.example.drugstore.presentation.adapter.NotificationAdapter
import com.example.drugstore.presentation.home.ProductDetailActivity
import com.example.drugstore.presentation.order.OrderStatusActivity
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    @Inject
    lateinit var notificationVM: NotificationVM
    private val notificationAdapter = NotificationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment

        onBindView()
        onBindEvent()
        return binding.root
    }

    private fun onBindView(){
        binding.rvNotification.adapter = notificationAdapter
        binding.rvNotification.layoutManager = LinearLayoutManager(context)
        notificationVM.getNotification().observe(viewLifecycleOwner){
            if (it != null) {
                notificationAdapter.setList(it)
            }
        }
    }

    private fun onBindEvent(){
        notificationAdapter.onItemClick = {notification ->
            when(notification.type){
                0 -> {
                    val intent = Intent(context,ProductDetailActivity::class.java)
                    intent.putExtra(Constants.PRODUCT_ID,notification.contentType.toInt())
                    startActivity(intent)
                }
                3 -> {
                    val intent = Intent(context,OrderStatusActivity::class.java)
                    intent.putExtra(Constants.ORDER_ID,notification.contentType)
                    startActivity(intent)
                }
            }
        }
    }
}