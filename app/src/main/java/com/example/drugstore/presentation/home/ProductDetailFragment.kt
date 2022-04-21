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
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import javax.inject.Inject
@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private var cartProduct: CartProduct? = null
    private lateinit var product: Product


    private lateinit var binding: FragmentProductDetailBinding
    @Inject lateinit var categoryVM: CategoryVM
    private lateinit var cartVM: CartVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartVM = ViewModelProvider(this,CartVM.CartProductVMFactory(requireActivity().application))[CartVM::class.java]
        if(arguments?.containsKey(Constants.OBJECT_PRODUCT) == true){
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
        categoryVM.getCategory(product.CatID).observe(viewLifecycleOwner){
            if (it != null) {
                binding.tvCatName.text = it.CatName
            }
        }
        binding.tvQuantity.text = "Quantity: ${product.Quantity}"
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

        cartVM.getProductById(product.ProID).observe(viewLifecycleOwner){
            cartProduct = it
            if(it != null){
                binding.tvProductQuantityInCart.text = it.Quantity.toString()
                binding.tvSumPrice.text = "${DecimalFormat("##,###").format(it.Quantity*product.Price.toDouble())}"
            }else{
                binding.tvProductQuantityInCart.text = "0"
                binding.tvSumPrice.text = "${DecimalFormat("##,###").format(product.Price.toDouble())}"
            }
        }

        cartVM.getCartProducts().observe(viewLifecycleOwner){
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
}