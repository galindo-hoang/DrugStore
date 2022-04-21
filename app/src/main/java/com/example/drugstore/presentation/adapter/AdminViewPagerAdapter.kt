package com.example.drugstore.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.drugstore.presentation.admin.chat.ChatAdminFragment
import com.example.drugstore.presentation.admin.home.HomeAdminFragment
import com.example.drugstore.presentation.admin.order.OrderAdminFragment
import com.example.drugstore.presentation.admin.ProfileAdminFragment

class AdminViewPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> HomeAdminFragment()
            1 -> OrderAdminFragment()
            2 -> ChatAdminFragment()
            3 -> ProfileAdminFragment()
            else -> HomeAdminFragment()
        }
    }
}