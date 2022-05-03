package com.example.drugstore.presentation.order

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.databinding.FragmentOrderBinding
import com.example.drugstore.presentation.adapter.OrderAdapter
import com.example.drugstore.presentation.adapter.SectionPageAdapter
import com.example.drugstore.utils.Constants
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderFragment : Fragment() {
    private lateinit var binding: FragmentOrderBinding
    private var orderAdapter = OrderAdapter()
    private lateinit var viewPagerAdapter: SectionPageAdapter

    @Inject
    lateinit var orderVM: OrderVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater,container,false)

        setupAdapter()
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty() && p0.toString().trim().length >= 3){
                    val search = p0.toString().trim().lowercase()
                    orderVM.getOrdersByProduct(search).observe(viewLifecycleOwner){
                        if (it != null) {
                            orderAdapter.setList(it)
                        }
                    }
                    binding.rvOrder.visibility = View.VISIBLE
                    binding.ll.visibility = View.GONE
                }else{

                    binding.rvOrder.visibility = View.GONE
                    binding.ll.visibility = View.VISIBLE
                }
            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.tablayout.setupWithViewPager(binding.viewpager)
        binding.tablayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewpager.currentItem = tab.position
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun setupAdapter() {
        viewPagerAdapter = SectionPageAdapter(childFragmentManager)
        viewPagerAdapter.addFragment(OrderHistoryFragment(),"History")
        viewPagerAdapter.addFragment(OrderOngoingFragment(),"Ongoing")
        binding.viewpager.adapter = viewPagerAdapter


        orderAdapter.onItemClick = {order ->
            val intent = Intent(context,OrderStatusActivity::class.java)
            intent.putExtra(Constants.ORDER_ID,order.OrderID)
            startActivity(intent)
        }
        binding.rvOrder.adapter = orderAdapter
        binding.rvOrder.layoutManager = LinearLayoutManager(context)
    }

}