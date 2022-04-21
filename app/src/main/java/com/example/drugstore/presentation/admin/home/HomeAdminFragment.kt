package com.example.drugstore.presentation.admin.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentHomeAdminBinding
import com.example.drugstore.presentation.adapter.ProductAdapter
import com.example.drugstore.presentation.home.ProductVM
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class HomeAdminFragment : Fragment() {
    private lateinit var binding:FragmentHomeAdminBinding
    @Inject
    lateinit var productVM: ProductVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeAdminBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment

        val productAdapter = ProductAdapter()
        productAdapter.onItemClick = {product ->
            val intent = Intent(context,AddProductActivity::class.java)
            intent.putExtra(Constants.OBJECT_PRODUCT,product)
            startActivity(intent)
        }
        binding.rvSearch.adapter = productAdapter
        binding.rvSearch.layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)

        binding.fab.setOnClickListener {
            startActivity(Intent(context,AddProductActivity::class.java))
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        productVM.getAllListProducts().observe(viewLifecycleOwner){
            Log.e("===============","eeeeeeeeee")
            if (it != null) {
                (binding.rvSearch.adapter as ProductAdapter).setList(it)
            }
        }
    }
}