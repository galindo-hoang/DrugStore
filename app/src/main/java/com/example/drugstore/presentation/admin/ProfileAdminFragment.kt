package com.example.drugstore.presentation.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentProfileAdminBinding

class ProfileAdminFragment : Fragment() {
    private lateinit var binding: FragmentProfileAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileAdminBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }
}