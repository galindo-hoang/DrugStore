package com.example.drugstore.presentation.home

import android.os.Bundle
import android.text.*
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drugstore.R
import com.example.drugstore.data.models.*
import com.example.drugstore.databinding.FragmentDrugByCategoryBinding
import com.example.drugstore.presentation.adapter.ProductAdapter
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DrugByCategoryFragment : Fragment() {
    private var listProduct: List<Product>? = null
    private lateinit var category: Category
    private lateinit var binding: FragmentDrugByCategoryBinding
    @Inject
    lateinit var productVM: ProductVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            if (it != null) {
                productAdapter.setList(it)
            }
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
}