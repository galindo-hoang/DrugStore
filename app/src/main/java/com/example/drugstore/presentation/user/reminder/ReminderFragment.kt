package com.example.drugstore.presentation.user.reminder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentReminderBinding
import com.example.drugstore.presentation.adapter.PrescriptionAdapter
import com.example.drugstore.presentation.home.HomeActivity

class ReminderFragment : Fragment() {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PrescriptionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        val view = binding.root
        bindComponents()
        observeViewModel()
        return view
    }

    private fun observeViewModel() {
        adapter = PrescriptionAdapter().apply {

        }


    }

    private fun bindComponents() {
        binding.run {
            btnAdd.setOnClickListener {
                val activity = requireActivity() as PrescriptionActivity
                activity.replaceFragment(NewPrescriptionFragment())
            }
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