package com.example.drugstore.presentation.notify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.databinding.FragmentNotificationBinding
import com.example.drugstore.presentation.adapter.NotificationAdapter
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
}