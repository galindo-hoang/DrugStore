package com.example.drugstore.presentation.user

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.drugstore.databinding.FragmentNewPrescriptionBinding
import com.example.drugstore.presentation.utils.DatePickerDialogFactory
import com.example.drugstore.presentation.utils.TimePickerDialogFactory
import com.example.drugstore.presentation.utils.toTwoDigitString
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NewPrescriptionFragment : Fragment() {
    private var _binding: FragmentNewPrescriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var addDrugLauncher: ActivityResultLauncher<Intent>
    private lateinit var startDateCalendar: DatePickerDialog
    private lateinit var endDateCalendar: DatePickerDialog
    private lateinit var timePicker: TimePickerDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPrescriptionBinding.inflate(
            inflater,
            container,
            false
        )
        val view = binding.root
        initResource();
        bindComponents()

        return view;
    }

    @SuppressLint("SetTextI18n")
    private fun initResource() {
        addDrugLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {

                }
            }

        startDateCalendar = DatePickerDialogFactory.create(requireContext()) {
            binding.textViewContentStartDate.text = SimpleDateFormat(
                "dd-MM-yyyy",
                Locale.getDefault()
            ).format(DatePickerDialogFactory.cal.time)

        }
        endDateCalendar = DatePickerDialogFactory.create(requireContext()) {
            binding.textViewContentEndDate.text = SimpleDateFormat(
                "dd-MM-yyyy",
                Locale.getDefault()
            ).format(DatePickerDialogFactory.cal.time)
        }
        timePicker = TimePickerDialogFactory.create(requireContext()) {
            binding.textViewContentTime.text =
                TimePickerDialogFactory.lastHour.toTwoDigitString() +
                        ":${TimePickerDialogFactory.lastMinute.toTwoDigitString()}"
        }
    }

    private fun bindComponents() {
        binding.run {
            btnStartDatePicker.setOnClickListener {
                startDateCalendar.show()
            }
            btnEndDatePicker.setOnClickListener {
                endDateCalendar.show()
            }
            btnTimePicker.setOnClickListener {
                timePicker.show()
            }
        }
    }
}