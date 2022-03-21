package com.example.drugstore.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val fragList:ArrayList<Fragment> = ArrayList<Fragment>()
    private val titleList:ArrayList<String> = ArrayList<String>()
    override fun getCount(): Int {
        return fragList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList.get(position)
    }
    fun addFragment(fragment:Fragment,title:String) {
        fragList.add(fragment)
        titleList.add(title)
    }

}