package com.example.drugstore.presentation.user.reminder

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.databinding.FragmentNewPrescriptionBinding
import com.example.drugstore.presentation.adapter.NewPrescriptionAdapter
import com.example.drugstore.presentation.home.HomeActivity
import com.example.drugstore.presentation.utils.DatePickerDialogFactory
import com.example.drugstore.presentation.utils.TimePickerDialogFactory
import com.example.drugstore.presentation.utils.toTwoDigitString
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class NewPrescriptionFragment : Fragment() {
    private var _binding: FragmentNewPrescriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var startDateCalendar: DatePickerDialog
    private lateinit var endDateCalendar: DatePickerDialog
    private lateinit var timePicker: TimePickerDialog
    private lateinit var newPrescriptionAdapter: NewPrescriptionAdapter

    @Inject
    lateinit var prescriptionVM: NewPrescriptionVM

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
        observeData()
        initResource()
        bindComponents()

        return view;
    }

    @SuppressLint("SetTextI18n")
    private fun observeData() {
        prescriptionVM.startDate.observe(viewLifecycleOwner) { startDate ->
            binding.textViewContentStartDate.text = SimpleDateFormat(
                "dd-MM-yyyy",
                Locale.getDefault()
            ).format(startDate)
        }

        prescriptionVM.name.observe(viewLifecycleOwner) { name ->
            binding.etName.setText(name)
        }

        prescriptionVM.endDate.observe(viewLifecycleOwner) { endDate ->
            binding.textViewContentEndDate.text = SimpleDateFormat(
                "dd-MM-yyyy",
                Locale.getDefault()
            ).format(endDate)
        }

        prescriptionVM.time.observe(viewLifecycleOwner) { time ->
            binding.textViewContentTime.text =
                time.first.toTwoDigitString() +
                        ":${time.second.toTwoDigitString()}"
        }

        newPrescriptionAdapter = NewPrescriptionAdapter().apply {
            onIncreaseClick = { prescriptionVM.increaseQuantity(it) }
            onDecreaseClick = { prescriptionVM.decreaseQuantity(it) }
            onDeleteClick = { prescriptionVM.deletePrescription(it) }
        }

        prescriptionVM.prescriptionDetails.observe(viewLifecycleOwner) {
            newPrescriptionAdapter.setList(it)
        }
    }

    private fun initResource() {
        startDateCalendar = DatePickerDialogFactory.create(requireContext()) {
            prescriptionVM.updateStartDate(DatePickerDialogFactory.getDate())
        }
        endDateCalendar = DatePickerDialogFactory.create(requireContext()) {
            prescriptionVM.updateEndDate(DatePickerDialogFactory.getDate())
        }
        timePicker = TimePickerDialogFactory.create(requireContext()) {
            prescriptionVM.updateTime(TimePickerDialogFactory.getTime())
        }
    }

    private fun bindComponents() {
        binding.run {
            btnStartDatePicker.setOnClickListener {
                DatePickerDialogFactory.setPreviousDate(prescriptionVM.startDate.value!!)
                startDateCalendar.show()
            }
            btnEndDatePicker.setOnClickListener {
                DatePickerDialogFactory.setPreviousDate(prescriptionVM.endDate.value!!)
                endDateCalendar.show()
            }
            btnTimePicker.setOnClickListener {
                TimePickerDialogFactory.setPreviousTime(prescriptionVM.time.value!!)
                timePicker.show()
            }
            btnAddMedicine.setOnClickListener {
                val prescriptionActivity = activity as PrescriptionActivity
                prescriptionActivity.replaceFragment(RemindDrugFragment())
            }
            btnSave.setOnClickListener {
                prescriptionVM.savePrescription(::onSavePrescriptionSuccess)
            }
            btnBack.setOnClickListener {
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            rvMedicines.apply {
                adapter = newPrescriptionAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun onSavePrescriptionSuccess(id: String) {
        val activity = activity as PrescriptionActivity
        activity.replaceFragment(ReminderDetailFragment.newInstance(id))
    }

    override fun onStart() {
        super.onStart()
        prescriptionVM.getPrescription()
    }

    override fun onPause() {
        super.onPause()
        prescriptionVM.updatePrescription()
    }
}