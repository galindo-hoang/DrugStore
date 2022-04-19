package com.example.drugstore.presentation.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentDrugByCategoryBinding
import com.example.drugstore.data.models.Category
import com.example.drugstore.data.models.Product
import com.example.drugstore.presentation.adapter.ProductAdapter
import com.example.drugstore.utils.Constants

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DrugByCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DrugByCategoryFragment : Fragment() {
    private var listProduct: List<Product>? = null
    private lateinit var category: Category
    private lateinit var binding: FragmentDrugByCategoryBinding
    private val productVM: ProductVM by activityViewModels()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        if(arguments?.containsKey(Constants.OBJECT_CATEGORY) == true){
            category = arguments?.getParcelable(Constants.OBJECT_CATEGORY)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDrugByCategoryBinding.inflate(inflater,container,false)

        binding.tvToolbar.text = category.CatName
        binding.tb.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        val productAdapter = ProductAdapter()
        productVM.getListProductsByCategory(category.CatID).observe(viewLifecycleOwner){
            listProduct = it
            productAdapter.setList(it)
        }
        productAdapter.onItemClick = {product -> transitProductDetail(product) }
        binding.rvProduct.adapter = productAdapter
        binding.rvProduct.layoutManager = GridLayoutManager(context,2)



        binding.etSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty() && p0.toString().trim().length >= 3){
                    val search = p0.toString().trim().lowercase()
                    val listSearch = listProduct?.filter { it.ProName.lowercase().contains(search) }
                    if (listSearch != null) {
                        productAdapter.setList(listSearch)
                    }
                }else{
                    listProduct?.let { productAdapter.setList(it) }
                }
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun transitProductDetail(product: Product) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putParcelable(Constants.OBJECT_PRODUCT,product)
        val fragment = ProductDetailFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.fragmentBottomNav,fragment)
        fragmentTransaction.addToBackStack("DrugDetail")
        fragmentTransaction.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DrugByCategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DrugByCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}