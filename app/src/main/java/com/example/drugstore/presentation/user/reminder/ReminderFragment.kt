package com.example.drugstore.presentation.user.reminder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentReminderBinding
import com.example.drugstore.presentation.adapter.PrescriptionAdapter
import com.example.drugstore.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReminderFragment : Fragment() {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PrescriptionAdapter

    @Inject
    lateinit var viewModel: ReminderVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        val view = binding.root
        observeViewModel()
        bindComponents()
        return view
    }

    private fun observeViewModel() {
        adapter = PrescriptionAdapter().apply {
            onEditClick = {
                val activity = requireActivity() as PrescriptionActivity
                activity.replaceFragment(ReminderDetailFragment.newInstance(it.id!!))
            }

            onDeleteClick = {
                viewModel.delete(it)
            }
        }

        viewModel.prescriptions.observe(viewLifecycleOwner) { prescriptions ->
            adapter.setList(prescriptions)
        }
    }

    private fun bindComponents() {
        binding.run {
            recyclerViewReminders.run {
                adapter = this@ReminderFragment.adapter
                layoutManager = LinearLayoutManager(context)
            }
            btnAdd.setOnClickListener {
                val activity = requireActivity() as PrescriptionActivity
                activity.replaceFragment(NewPrescriptionFragment())
            }
            btnBack.setOnClickListener {
                back()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchPrescription()
    }

    fun back() {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.putExtra(HomeActivity.IS_PROFILE, true)
        startActivity(intent)
        requireActivity().finish()
    }
}