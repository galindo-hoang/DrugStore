package com.example.drugstore.presentation.user

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentRemindDrugBinding
import com.example.drugstore.presentation.adapter.ProductAdapter
import com.example.drugstore.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RemindDrugFragment : Fragment() {
    private var _binding: FragmentRemindDrugBinding? = null
    private val binding get() = _binding!!
    private lateinit var productAdapter: ProductAdapter

    @Inject
    lateinit var remindDrugVM: RemindDrugViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRemindDrugBinding.inflate(inflater, container, false)
        val view = binding.root
        observeViewModel()
        bindComponents()
        onScrollChanged(view)
        return view
    }

    private fun onScrollChanged(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setOnScrollChangeListener { _, i, i2, i3, i4 ->
                Log.d("HAGLAS", "onScrollChanged")
            }
        }
    }

    private fun observeViewModel() {
        productAdapter = ProductAdapter().apply {
            onItemClick = { product ->
                remindDrugVM.addProduct(product)
                back()
            }
        }

        remindDrugVM.shownMedicines.observe(viewLifecycleOwner) { medicines ->
            productAdapter.setList(medicines)
        }
    }

    private fun bindComponents() {
        binding.run {
            btnBack.setOnClickListener {
                back()
            }
            rvMedicines.adapter = productAdapter
        }
    }

    private fun back() {
        val homeActivity = activity as HomeActivity
        homeActivity.replaceFragment(NewPrescriptionFragment())
    }

    override fun onStart() {
        super.onStart()
        remindDrugVM.getProducts()
    }
}