package com.example.drugstore.presentation.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentProfileBinding
import com.example.drugstore.presentation.BaseActivity
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

        bindComponents();
        return view
    }

    private fun bindComponents() {
        binding.btnLogout.setOnClickListener {
            profileVM.signOut(activity as BaseActivity)
        }
    }

    override fun onDestroy() {
        _binding = null;
        super.onDestroy()
    }
}