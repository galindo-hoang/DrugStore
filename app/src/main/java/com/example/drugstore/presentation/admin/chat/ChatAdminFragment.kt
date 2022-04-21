package com.example.drugstore.presentation.admin.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentChatAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatAdminFragment : Fragment() {
    private lateinit var binding:FragmentChatAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatAdminBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }
}