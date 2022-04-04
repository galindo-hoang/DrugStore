package com.example.drugstore.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.R
import com.example.drugstore.presentation.adapter.CategoryAdapter
import com.example.drugstore.presentation.adapter.ProductAdapter
import com.example.drugstore.databinding.FragmentHomeBinding
import com.example.drugstore.data.models.Category
import com.example.drugstore.utils.Constants

//import androidx.lifecycle.ViewModelProviders

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fm: FragmentManager
    private val categoryVM: CategoryVM by activityViewModels()
    private val productVM: ProductVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        fm = childFragmentManager

        fm.addOnBackStackChangedListener {
            Log.e("----size",fm.backStackEntryCount.toString())
            for (entry in 0 until fm.backStackEntryCount) {
                Log.e("----", "Found fragment: " + fm.getBackStackEntryAt(entry).name)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        val productTrendingAdapter = ProductAdapter()
        productVM.getAllListProducts().observe(viewLifecycleOwner){
            productTrendingAdapter.setList(it.subList(0,10))
        }
        binding.rvTopTrending.adapter = productTrendingAdapter
        binding.rvTopTrending.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)


        val productSupplementAdapter = ProductAdapter()
        productVM.getAllListProducts().observe(viewLifecycleOwner){
            productSupplementAdapter.setList(it.subList(it.size/2,it.size/2 + 6))
        }
        binding.rvSupplement.adapter = productSupplementAdapter
        binding.rvSupplement.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)


        val adapterCate = CategoryAdapter()

        categoryVM.getListCategories().observe(viewLifecycleOwner){
            adapterCate.setList(it.subList(0,4))
        }
        adapterCate.onItemClick = {category -> setUpTransitToDrugByCategoryFragment(category) }
        binding.rvCategory.adapter = adapterCate
        binding.rvCategory.layoutManager = GridLayoutManager(context,2,
            GridLayoutManager.HORIZONTAL,false)



//        binding.rvNews.adapter = NewsAdapter(a)
//        binding.rvNews.layoutManager = LinearLayoutManager(context,
//            LinearLayoutManager.HORIZONTAL,false)

        binding.clHeaderCategory.setOnClickListener {
            setUpChangeFragment()
        }

        // Inflate the layout for this fragment
        return binding.root
    }


    private fun setUpTransitToDrugByCategoryFragment(category: Category) {
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

    private fun setUpChangeFragment() {

        val parentFM = parentFragmentManager
        val parentFMTransition = parentFM.beginTransaction()
        parentFMTransition.replace(R.id.fragmentBottomNav,CategoryFragment())
        parentFMTransition.addToBackStack("CategoryFragment")
        parentFMTransition.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}