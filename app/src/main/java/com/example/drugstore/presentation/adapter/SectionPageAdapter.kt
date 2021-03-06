package com.example.drugstore.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val fragList:ArrayList<Fragment> = ArrayList()
    private val titleList:ArrayList<String> = ArrayList()
    override fun getCount(): Int {
        return fragList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragList[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }
    fun addFragment(fragment:Fragment,title:String) {
        fragList.add(fragment)
        titleList.add(title)
    }

}