package com.example.drugstore.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.R
import com.example.drugstore.adapter.CategoryAdapter
import com.example.drugstore.adapter.NewsAdapter
import com.example.drugstore.adapter.ProductAdapter
import com.example.drugstore.databinding.FragmentMainBinding
import com.example.drugstore.models.Category
import com.example.drugstore.utils.Constants

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container,false)

        val a = listOf("a","a","a","a","a","a")
        binding.rvTopTrending.adapter = ProductAdapter(a)
        binding.rvTopTrending.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)


        binding.rvSupplement.adapter = ProductAdapter(a)
        binding.rvSupplement.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)

        val adapterCate = CategoryAdapter(a)
        adapterCate.onItemClick = {category -> setUpTransitToDrugByCategoryFragment(category) }
        binding.rvCategory.adapter = adapterCate
        binding.rvCategory.layoutManager = GridLayoutManager(context,2,
            GridLayoutManager.HORIZONTAL,false)



        binding.rvNews.adapter = NewsAdapter(a)
        binding.rvNews.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)

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
        parentFMTransition.replace(R.id.fmTransition,fragment)
        parentFMTransition.addToBackStack("DrugByCategoryFragment")
        parentFMTransition.commit()
    }

    private fun setUpChangeFragment() {

        val parentFM = parentFragmentManager
        val parentFMTransition = parentFM.beginTransaction()
        parentFMTransition.replace(R.id.fmTransition,CategoryFragment())
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
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}