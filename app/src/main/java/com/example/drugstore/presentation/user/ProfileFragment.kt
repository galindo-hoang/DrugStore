package com.example.drugstore.presentation.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentProfileBinding
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.home.CartVM
import com.example.drugstore.presentation.user.address.AddressActivity
import com.example.drugstore.presentation.user.reminder.PrescriptionActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var profileVM: ProfileVM
    @Inject
    lateinit var cartVM: CartVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        bindComponents()
        return view
    }

    private fun bindComponents() {
        binding.run {
            btnLogout.setOnClickListener {
                profileVM.signOut(activity as BaseActivity)
                cartVM.deleteAll()
            }
            cvUpdateProfile.setOnClickListener {
                startActivity(Intent(context, UpdateProfileActivity::class.java))
            }
            btnReminder.setOnClickListener {
                val intent = Intent(activity, PrescriptionActivity::class.java)
                intent.putExtra(PrescriptionActivity.REMINDER_FRAGMENT, true)
                startActivity(intent)
                requireActivity().finish()
            }
            btnMyAddress.setOnClickListener {
                val intent = Intent(activity, AddressActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
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


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}