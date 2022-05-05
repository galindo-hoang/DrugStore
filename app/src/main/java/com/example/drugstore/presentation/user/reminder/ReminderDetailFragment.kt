package com.example.drugstore.presentation.user.reminder

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drugstore.databinding.FragmentReminderDetailBinding
import com.example.drugstore.presentation.adapter.ReminderDetailAdapter
import com.example.drugstore.presentation.utils.toTwoDigitString
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class ReminderDetailFragment private constructor() : Fragment() {
    private var _binding: FragmentReminderDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var reminderDetailAdapter: ReminderDetailAdapter
    var prescriptionId = ""

    @Inject
    lateinit var reminderDetailVM: ReminderDetailVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderDetailBinding.inflate(
            inflater,
            container,
            false
        )
        val view = binding.root
        observeViewModel()
        bindComponents()
        return view
    }

    private fun bindComponents() {
        binding.run {
            btnBack.setOnClickListener {
                back()
            }
            rvMedicines.adapter = reminderDetailAdapter
        }
    }

    private fun back() {
        val prescriptionActivity = activity as PrescriptionActivity
        prescriptionActivity.replaceFragment(ReminderFragment())
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun observeViewModel() {
        reminderDetailVM.run {
            startDate.observe(viewLifecycleOwner) { date ->
                binding.tvStartDate.text = SimpleDateFormat("dd-MM-yyyy").format(date)
            }
            name.observe(viewLifecycleOwner) { name->
                binding.tvName.text = name
            }
            endDate.observe(viewLifecycleOwner) { date ->
                binding.tvEndDate.text = SimpleDateFormat("dd-MM-yyyy").format(date)
            }
            time.observe(viewLifecycleOwner) { time ->
                binding.tvTime.text =
                    time.first.toTwoDigitString() +
                            ":${time.second.toTwoDigitString()}"
            }
            reminderDetailAdapter = ReminderDetailAdapter().apply {
                onItemClick = {
                    val prescriptionActivity = activity as PrescriptionActivity
                    prescriptionActivity.replaceFragment(
                        MedicineDetailFragment.newInstance(
                            it.productId!!,
                            prescriptionId
                        )
                    )
                }
            }

            prescriptionDetails.observe(viewLifecycleOwner) { prescriptionDetails ->
                reminderDetailAdapter.setList(prescriptionDetails)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        reminderDetailVM.getPrescription(prescriptionId)
    }

    companion object {
        fun newInstance(id: String) =
            ReminderDetailFragment().apply {
                prescriptionId = id
            }
    }
}