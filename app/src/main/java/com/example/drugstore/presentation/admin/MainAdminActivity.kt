package com.example.drugstore.presentation.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.example.drugstore.R
import com.example.drugstore.databinding.ActivityMainAdminBinding
import com.example.drugstore.presentation.adapter.AdminViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPagerAdapter = AdminViewPagerAdapter(supportFragmentManager)
        binding.vp.adapter = viewPagerAdapter
        binding.vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> binding.bnView.menu.findItem(R.id.navBtnHomeAdmin).isChecked = true
                    1 -> binding.bnView.menu.findItem(R.id.navBtnOrderAdmin).isChecked = true
                    2 -> binding.bnView.menu.findItem(R.id.navBtnChatAdmin).isChecked = true
                    3 -> binding.bnView.menu.findItem(R.id.navBtnProfileAdmin).isChecked = true
                }
            }
        })
        binding.bnView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navBtnHomeAdmin -> binding.vp.currentItem = 0
                R.id.navBtnOrderAdmin -> binding.vp.currentItem = 1
                R.id.navBtnChatAdmin -> binding.vp.currentItem = 2
                R.id.navBtnProfileAdmin -> binding.vp.currentItem = 3
            }
            true
        }
    }
}