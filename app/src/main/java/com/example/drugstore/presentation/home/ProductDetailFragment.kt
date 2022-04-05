package com.example.drugstore.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.drugstore.R
import com.example.drugstore.data.models.CartProduct
import com.example.drugstore.data.models.Product
import com.example.drugstore.databinding.FragmentProductDetailBinding
import com.example.drugstore.presentation.adapter.NutritionAdapter
import com.example.drugstore.presentation.adapter.ProductAdapter
import com.example.drugstore.utils.Constants
import java.text.DecimalFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductDetailFragment : Fragment() {
    private var cartProduct: CartProduct? = null
    private lateinit var product: Product

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentProductDetailBinding
    private val categoryVM: CategoryVM by activityViewModels()
    private lateinit var cartVM: CartVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        cartVM = ViewModelProvider(this,CartVM.CartProductVMFactory(requireActivity().application))[CartVM::class.java]
        if(requireArguments().containsKey(Constants.OBJECT_PRODUCT)){
            product = arguments?.getParcelable(Constants.OBJECT_PRODUCT)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding=FragmentProductDetailBinding.inflate(inflater,container,false)

        setUpView()

        setUpEvent()

        return binding.root
    }

    private fun setUpEvent() {
        binding.btnMinus.setOnClickListener {
            if(cartProduct != null) cartVM.decreaseQuantityProduct(cartProduct!!.Quantity,product.ProID)
        }

        binding.btnAdd.setOnClickListener {
            if(cartProduct == null) cartVM.insertProduct(product)
            else cartVM.increaseQuantityProduct(cartProduct!!.Quantity,product.ProID)
        }

        binding.btnCartTop.setOnClickListener {
            transitCart()
        }
    }

    private fun setUpView() {
        Glide.with(this)
            .load(product.ProImage)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.ivPro)
        categoryVM.fetchCategory(product.CatID).observe(viewLifecycleOwner){
            binding.tvCatName.text = it.CatName
        }
        binding.tvQuantity.text = "Quantity: ${product.Quantity.toString()}"
        binding.tvDes.text = product.Description
        binding.tvProName.text = product.ProName


        binding.rcProductDetail.adapter = NutritionAdapter(product.NutritionList)
        binding.rcProductDetail.layoutManager = LinearLayoutManager(context)

        cartVM.getQuantityProducts().observe(viewLifecycleOwner){
            if(it > 0){
                binding.tvQuantityProduct.visibility = View.VISIBLE
                binding.tvQuantityProduct.text = it.toString()
            }else{
                binding.tvQuantityProduct.visibility = View.INVISIBLE
            }
        }

        cartVM.fetchProductById(product.ProID).observe(viewLifecycleOwner){
            cartProduct = it
            if(it != null){
                binding.tvProductQuantityInCart.text = it.Quantity.toString()
                binding.tvSumPrice.text = "${DecimalFormat("##,###").format(it.Quantity*product.Price.toDouble())}"
            }else{
                binding.tvProductQuantityInCart.text = "0"
                binding.tvSumPrice.text = "${DecimalFormat("##,###").format(product.Price.toDouble())}"
            }
        }

        cartVM.fetchCartProducts().observe(viewLifecycleOwner){
            for(i in it){
                Log.e("---------",i.toString())
            }
        }
    }



    private fun transitCart() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentBottomNav,CartFragment())
        fragmentTransaction.addToBackStack("CartFragment")
        fragmentTransaction.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}