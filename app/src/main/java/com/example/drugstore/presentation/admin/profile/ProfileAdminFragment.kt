package com.example.drugstore.presentation.admin.profile

import android.os.Bundle
import android.util.Log
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
        setUpTransactionFragment(ProfileSettingAdminFragment())
        return binding.root
    }

    private fun setUpTransactionFragment(fragment: Fragment) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flProfileAdmin, fragment)
        fragmentTransaction.addToBackStack(fragment.tag)
        fragmentTransaction.commit()
    }
}