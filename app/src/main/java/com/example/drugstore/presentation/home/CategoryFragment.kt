package com.example.drugstore.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drugstore.R
import com.example.drugstore.presentation.adapter.CategoryAdapter
import com.example.drugstore.databinding.FragmentCategoryBinding
import com.example.drugstore.data.models.Category
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    @Inject
    lateinit var categoryVM:CategoryVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater,container,false)


        val adapter = CategoryAdapter()
        categoryVM.getAllCategories().observe(viewLifecycleOwner){
            if (it != null) {
                adapter.setList(it)
            }
        }
        adapter.onItemClick = { category -> setUpDrugByCategoryFragment(category) }
        binding.rvCategory.adapter = adapter
        binding.rvCategory.layoutManager = GridLayoutManager(context,2)

        binding.tb.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpDrugByCategoryFragment(category: Category) {
        val parentFM = parentFragmentManager
        val parentFMTransition = parentFM.beginTransaction()
        val bundle = Bundle()
        bundle.putParcelable(Constants.OBJECT_CATEGORY,category)
        val fragment = DrugByCategoryFragment()
        fragment.arguments = bundle
        parentFMTransition.replace(R.id.fragmentBottomNav,fragment)
        parentFMTransition.addToBackStack("DrugByCategoryFragment")
        parentFMTransition.commit()

    }

}