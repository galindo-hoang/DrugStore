package com.example.drugstore.presentation.user.reminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentMedicineDetailBinding
import com.example.drugstore.presentation.adapter.IngredientAdapter
import com.example.drugstore.presentation.adapter.NutritionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MedicineDetailFragment private constructor() : Fragment() {
    // On initialized
    var medicineId: Int = 0
    var prescriptionId: String = ""

    private var _binding: FragmentMedicineDetailBinding? = null
    val binding get() = _binding!!
    private lateinit var nutritionAdapter: NutritionAdapter
    private lateinit var ingredientAdapter: IngredientAdapter

    @Inject
    lateinit var medicineDetailVM: MedicineDetailVM

    override fun onStart() {
        super.onStart()
        medicineDetailVM.getMedicine(medicineId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicineDetailBinding.inflate(
            inflater,
            container,
            false
        )
        val view = binding.root
        observeViewModel()
        bindingComponents()
        return view
    }

    private fun observeViewModel() {
        nutritionAdapter = NutritionAdapter()
        ingredientAdapter = IngredientAdapter()

        medicineDetailVM.run {
            medicine.observe(viewLifecycleOwner) { product ->
                binding.tvProName.text = product.ProName
                binding.tvDes.text = product.Description
                binding.tvCatName.text = product.category?.CatName ?: ""
                nutritionAdapter.setList(product.NutritionList)
                ingredientAdapter.setList(product.IngredientList)
            }
            productImage.observe(viewLifecycleOwner) { placeholder -> placeholder.into(binding.ivPro) }
        }
    }

    private fun bindingComponents() {
        binding.run {
            rcProductDetail.run {
                adapter = nutritionAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            rcvIngredient.run {
                adapter = ingredientAdapter
            }
            btnBack.setOnClickListener {
                back()
            }
        }
    }

    private fun back() {
        val prescriptionActivity = requireActivity() as PrescriptionActivity
        prescriptionActivity.replaceFragment(
            ReminderDetailFragment.newInstance(prescriptionId),
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Int, prescriptionId: String) =
            MedicineDetailFragment().apply {
                this.prescriptionId = prescriptionId
                medicineId = id
            }
    }
}