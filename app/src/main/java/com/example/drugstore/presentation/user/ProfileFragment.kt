package com.example.drugstore.presentation.user

import android.app.DatePickerDialog
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
import com.example.drugstore.presentation.user.reminder.PrescriptionActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var profileVM: ProfileVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        setData()
        bindComponents()
        return view
    }

    private fun bindComponents() {
        binding.btnLogout.setOnClickListener {
            profileVM.signOut(activity as BaseActivity)
        }
        binding.cvUpdateProfile.setOnClickListener {
            setUpTransactionFragment(UpdateProfileFragment())
        }
        binding.btnReminder.setOnClickListener {
            val intent = Intent(activity, PrescriptionActivity::class.java)
            intent.putExtra(PrescriptionActivity.REMINDER_FRAGMENT, true)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun setData() {
        profileVM.getUserByID().observe(viewLifecycleOwner) {
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
        fragmentTransaction.replace(R.id.fragmentBottomNav, fragment)
        fragmentTransaction.addToBackStack(fragment.tag)
        fragmentTransaction.commit()
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}