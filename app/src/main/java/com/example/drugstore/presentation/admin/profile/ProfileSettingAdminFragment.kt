package com.example.drugstore.presentation.admin.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentProfileSettingAdminBinding
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.user.ProfileVM
import com.example.drugstore.presentation.user.UpdateProfileActivity
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
        return binding.root
    }

    private fun bindComponents() {
        binding.btnLogOut.setOnClickListener {
            profileVM.signOut(activity as BaseActivity)
        }
        binding.cvUpdateProfile.setOnClickListener {
            startActivity(Intent(context,UpdateProfileActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        setData()
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
}