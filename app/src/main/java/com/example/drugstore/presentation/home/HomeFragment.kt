package com.example.drugstore.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.R
import com.example.drugstore.presentation.adapter.CategoryAdapter
import com.example.drugstore.presentation.adapter.ProductAdapter
import com.example.drugstore.databinding.FragmentHomeBinding
import com.example.drugstore.data.models.Category
import com.example.drugstore.data.models.Product
import com.example.drugstore.presentation.adapter.NewsTopicAdapter
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
    private val categoryVM: CategoryVM by activityViewModels()
    private val productVM: ProductVM by activityViewModels()
    private val newsVM: NewsVM by activityViewModels()
    private var cartVM: CartVM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        cartVM = ViewModelProvider(this,CartVM.CartProductVMFactory(requireActivity().application))[CartVM::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)


        binding.btnCart.setOnClickListener {
            transitCart()
        }
        cartVM!!.getQuantityProducts().observe(viewLifecycleOwner){
            if(it > 0) {
                binding.tvQuantityProduct.visibility = View.VISIBLE
                binding.tvQuantityProduct.text = it.toString()
            }else{
                binding.tvQuantityProduct.visibility = View.INVISIBLE
            }
        }



        val productTrendingAdapter = ProductAdapter()
        productVM.getAllListProducts().observe(viewLifecycleOwner){
            productTrendingAdapter.setList(it.subList(0,10))
        }
        productTrendingAdapter.onItemClick = {product -> transitProductDetail(product) }
        binding.rvTopTrending.adapter = productTrendingAdapter
        binding.rvTopTrending.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)




        val productSupplementAdapter = ProductAdapter()
        productVM.getAllListProducts().observe(viewLifecycleOwner){
            productSupplementAdapter.setList(it.subList(it.size/2,it.size/2 + 6))
        }
        productSupplementAdapter.onItemClick = {product -> transitProductDetail(product) }
        binding.rvSupplement.adapter = productSupplementAdapter
        binding.rvSupplement.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)




        val adapterCate = CategoryAdapter()
        categoryVM.getAllCategories().observe(viewLifecycleOwner){
            adapterCate.setList(it.subList(0,4))
        }
        adapterCate.onItemClick = {category -> setUpTransitToDrugByCategoryFragment(category) }
        binding.rvCategory.adapter = adapterCate
        binding.rvCategory.layoutManager = GridLayoutManager(context,2,
            GridLayoutManager.HORIZONTAL,false)


        val newsAdapter = NewsTopicAdapter()
        binding.rvNews.adapter = newsAdapter
        newsVM.getListTopics().observe(viewLifecycleOwner){
            newsAdapter.setList(it)
        }
        newsAdapter.onItemClick = {topicNews ->
            val fragment = NewsTopicFragment()
            val bundle = Bundle()
            bundle.putString(Constants.TOPIC_NAME, topicNews.topic)
            fragment.arguments = bundle
            setUpTransitionFragment(fragment)
        }
        binding.rvNews.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.clHeaderNews.setOnClickListener {
            setUpTransitionFragment(NewsTopicFragment())
        }


        binding.clHeaderCategory.setOnClickListener {
            setUpTransitionFragment(CategoryFragment())
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun transitCart() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentBottomNav,CartFragment())
        fragmentTransaction.addToBackStack("CartFragment")
        fragmentTransaction.commit()
    }

    private fun transitProductDetail(product: Product) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putParcelable(Constants.OBJECT_PRODUCT,product)
        val fragment = ProductDetailFragment()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.fragmentBottomNav,fragment)
        fragmentTransaction.addToBackStack("DrugDetailCate")
        fragmentTransaction.commit()
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

    private fun setUpTransitionFragment(fragment: Fragment) {
        val parentFM = parentFragmentManager
        val parentFMTransition = parentFM.beginTransaction()
        parentFMTransition.replace(R.id.fragmentBottomNav,fragment)
        parentFMTransition.addToBackStack("")
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