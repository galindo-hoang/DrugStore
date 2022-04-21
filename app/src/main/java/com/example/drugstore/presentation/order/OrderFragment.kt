package com.example.drugstore.presentation.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.drugstore.R
import com.example.drugstore.presentation.adapter.SectionPageAdapter
import com.google.android.material.tabs.TabLayout

class OrderFragment : Fragment() {
    var myFragment:View?=null
    var viewPage: ViewPager?=null
    var tabLayout: TabLayout?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myFragment= inflater.inflate(R.layout.fragment_order,container,false)
        viewPage=myFragment?.findViewById(R.id.viewpager)
        tabLayout=myFragment?.findViewById(R.id.tablayout)
        return myFragment
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpViewPager(viewPage)
        tabLayout?.setupWithViewPager(viewPage)
        tabLayout?.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPage!!.currentItem = tab.position
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun setUpViewPager(viewPage: ViewPager?) {
        val adapter = SectionPageAdapter(childFragmentManager)
        adapter.addFragment(OrderHistoryFragment(),"History")
        adapter.addFragment(OrderOngoingFragment(),"Ongoing")
        viewPage?.adapter=adapter
    }
}