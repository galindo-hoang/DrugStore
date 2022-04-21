package com.example.drugstore.presentation.user.reminder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentReminderBinding
import com.example.drugstore.presentation.home.HomeActivity

class ReminderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        val view = binding.root
        bindComponents()
        return view
    }

    private fun bindComponents() {
        binding.run {
            btnBack.setOnClickListener {
                back()
            }
        }
    }

    fun back() {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.putExtra(HomeActivity.IS_PROFILE, true)
        startActivity(intent)
        requireActivity().finish()
    }
}