package com.example.drugstore.presentation.order

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.databinding.FragmentOrderOngoingBinding
import com.example.drugstore.presentation.adapter.OrderAdapter
import com.example.drugstore.utils.Constants

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderOngoingFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentOrderOngoingBinding
    private val orderVM: OrderVM by activityViewModels()

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
    ): View? {
        binding = FragmentOrderOngoingBinding.inflate(inflater,container,false)

        val orderAdapter = OrderAdapter()
        orderVM.getOrderByUser(FirebaseClass.getCurrentUserId(),false).observe(viewLifecycleOwner){
            it?.let { it1 -> orderAdapter.setList(it1) }
        }

        orderAdapter.onItemClick = {order ->
            val intent = Intent(context,OrderStatusActivity::class.java)
            intent.putExtra(Constants.ORDER_ID,order.OrderID)
            startActivity(intent)
        }

        binding.rcViewOrderOngoing.adapter = orderAdapter
        binding.rcViewOrderOngoing.layoutManager = LinearLayoutManager(context)
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
            OrderOngoingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}