package com.example.drugstore.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.databinding.FragmentCartBinding
import com.example.drugstore.presentation.adapter.CartAdapter
import com.example.drugstore.presentation.order.OrderActivity
import com.example.drugstore.presentation.order.OrderFragment
import java.text.DecimalFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentCartBinding
    private var cartVM:CartVM? = null
    private var sum = 0

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
        binding = FragmentCartBinding.inflate(inflater,container,false)

        val cartAdapter = CartAdapter()
        cartVM!!.getCartProducts().observe(viewLifecycleOwner){
            cartAdapter.setList(it)
            sum = 0
            it.forEach { i ->
                sum += i.Quantity*i.Price
            }
            binding.tvRawPrice.text = "${DecimalFormat("##,###").format(sum)}"
            binding.tvSumPrice.text = "${DecimalFormat("##,###").format(sum)}"
        }
        binding.rcViewCart.adapter = cartAdapter
        binding.rcViewCart.layoutManager = LinearLayoutManager(context)
        // Inflate the layout for this fragment

        binding.btnContinue.setOnClickListener {
            if(sum == 0) {
                Toast.makeText(context,"Please chose product to continue to order",Toast.LENGTH_SHORT).show()
            }else startActivity(Intent(context,OrderActivity::class.java))
        }
        return binding.root
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
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}