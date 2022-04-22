package com.example.drugstore.presentation.admin.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentProfileAdminBinding
import com.example.drugstore.databinding.FragmentProfileSettingAdminBinding
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.user.ProfileVM
import com.example.drugstore.presentation.user.UpdateProfileFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileSettingAdminFragment : Fragment() {
    private lateinit var binding: FragmentProfileSettingAdminBinding
    @Inject
    lateinit var profileVM: ProfileVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileSettingAdminBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        bindComponents()
        setData()
        return binding.root
    }

    private fun bindComponents() {
        binding.btnLogOut.setOnClickListener {
            profileVM.signOut(activity as BaseActivity)
        }
        binding.cvUpdateProfile.setOnClickListener {
            setUpTransactionFragment(UpdateProfileFragment())
        }
    }

    private fun setData() {
        profileVM.getCurrentUser().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvName.text = it.UserName
                Glide.with(binding.root)
                    .load(it.UserImage)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.ivProfile)
            }
        }
    }

    private fun setUpTransactionFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flProfileAdmin, fragment)
        fragmentTransaction.addToBackStack(fragment.tag)
        fragmentTransaction.commit()
    }
}