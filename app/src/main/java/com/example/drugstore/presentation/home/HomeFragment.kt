package com.example.drugstore.presentation.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.R
import com.example.drugstore.data.models.Category
import com.example.drugstore.data.models.Product
import com.example.drugstore.databinding.FragmentHomeBinding
import com.example.drugstore.presentation.adapter.CategoryAdapter
import com.example.drugstore.presentation.adapter.NewsTopicAdapter
import com.example.drugstore.presentation.adapter.ProductAdapter
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var categoryVM: CategoryVM

    @Inject
    lateinit var productVM: ProductVM

    @Inject
    lateinit var cartVM: CartVM

    private val newsVM: NewsVM by activityViewModels()
    private val searchAdapter = ProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        binding.rvSearch.adapter = searchAdapter
        binding.rvSearch.layoutManager = GridLayoutManager(context, 2)
        searchAdapter.onItemClick = { product ->
            startActivityProductDetail(product)
        }
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrEmpty() && p0.toString().trim().length >= 3) {
                    val search = p0.toString().trim().lowercase()
                    productVM.getProductsWithSearch(search).observe(viewLifecycleOwner) {
                        if (it != null) {
                            searchAdapter.setList(it)
                        }
                    }
                    binding.rvSearch.visibility = View.VISIBLE
                    binding.llContent.visibility = View.GONE
                } else {
                    binding.rvSearch.visibility = View.GONE
                    binding.llContent.visibility = View.VISIBLE
                }
            }
        })


        binding.btnCart.setOnClickListener {
            startActivity(Intent(context,CartActivity::class.java))
        }
        cartVM.getQuantityProducts().observe(viewLifecycleOwner) {
            if (it > 0) {
                binding.tvQuantityProduct.visibility = View.VISIBLE
                binding.tvQuantityProduct.text = it.toString()
            } else {
                binding.tvQuantityProduct.visibility = View.INVISIBLE
            }
        }

        val productTrendingAdapter = ProductAdapter()
        productVM.getAllListProducts().observe(viewLifecycleOwner) {
            if (it != null) {
                productTrendingAdapter.setList(it.subList(0, 10))
            }
        }
        productTrendingAdapter.onItemClick = { product -> startActivityProductDetail(product) }
        binding.rvTopTrending.adapter = productTrendingAdapter
        binding.rvTopTrending.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )


        val productSupplementAdapter = ProductAdapter()
        productVM.getAllListProducts().observe(viewLifecycleOwner) {
            if (it != null) {
                productSupplementAdapter.setList(it.subList(it.size / 2, it.size / 2 + 6))
            }
        }
        productSupplementAdapter.onItemClick = { product -> startActivityProductDetail(product) }
        binding.rvSupplement.adapter = productSupplementAdapter
        binding.rvSupplement.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )


        val adapterCate = CategoryAdapter()
        categoryVM.getAllCategories().observe(viewLifecycleOwner) {
            if (it != null) {
                adapterCate.setList(it.subList(0, 4))
            }
        }
        adapterCate.onItemClick = { category -> setUpTransitToDrugByCategoryFragment(category) }
        binding.rvCategory.adapter = adapterCate
        binding.rvCategory.layoutManager = GridLayoutManager(
            context, 2,
            GridLayoutManager.HORIZONTAL, false
        )


        val newsAdapter = NewsTopicAdapter()
        binding.rvNews.adapter = newsAdapter
        newsVM.getListTopics().observe(viewLifecycleOwner) {
            newsAdapter.setList(it)
        }
        newsAdapter.onItemClick = { topicNews ->
            val fragment = NewsTopicFragment()
            val bundle = Bundle()
            bundle.putString(Constants.TOPIC_NAME, topicNews.topic)
            fragment.arguments = bundle
            setUpTransitionFragment(fragment)
        }
        binding.rvNews.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        binding.clHeaderNews.setOnClickListener {
            setUpTransitionFragment(NewsTopicFragment())
        }


        binding.clHeaderCategory.setOnClickListener {
            setUpTransitionFragment(CategoryFragment())
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun startActivityProductDetail(product: Product) {
        val intent = Intent(context,ProductDetailActivity::class.java)
        intent.putExtra(Constants.PRODUCT_ID,product.ProID)
        startActivity(intent)
    }

    private fun setUpTransitToDrugByCategoryFragment(category: Category) {
        val parentFM = parentFragmentManager
        val parentFMTransition = parentFM.beginTransaction()
        val bundle = Bundle()
        bundle.putParcelable(Constants.OBJECT_CATEGORY, category)
        val fragment = DrugByCategoryFragment()
        fragment.arguments = bundle
        parentFMTransition.replace(R.id.fragmentBottomNav, fragment)
        parentFMTransition.addToBackStack("DrugByCategoryFragment")
        parentFMTransition.commit()
    }

    private fun setUpTransitionFragment(fragment: Fragment) {
        val parentFM = parentFragmentManager
        val parentFMTransition = parentFM.beginTransaction()
        parentFMTransition.replace(R.id.fragmentBottomNav, fragment)
        parentFMTransition.addToBackStack("")
        parentFMTransition.commit()
    }
}