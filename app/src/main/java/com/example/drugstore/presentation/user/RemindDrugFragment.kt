package com.example.drugstore.presentation.user

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        return view
    }

    private fun observeViewModel() {
        productAdapter = ProductAdapter().apply {
            onItemClick = { product ->
                remindDrugVM.addProduct(product, ::back)
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
            rvMedicines.apply {
                adapter = productAdapter

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(
                            recyclerView: RecyclerView,
                            dx: Int,
                            dy: Int
                        ) {
                            recyclerView.run {
                                val offset = computeVerticalScrollOffset()
                                val height = computeVerticalScrollRange()
                                val thumbSize = computeVerticalScrollExtent()
                                val load = offset + 2 * thumbSize >= height
                                if (load) {
                                    remindDrugVM.getProducts()
                                }
                            }
                        }
                    })

                }
            }
        }
    }


    private fun back() {
        val prescriptionActivity = activity as PrescriptionActivity
        prescriptionActivity.replaceFragment(NewPrescriptionFragment())
    }

    override fun onStart() {
        super.onStart()
        remindDrugVM.getProducts()
    }
}