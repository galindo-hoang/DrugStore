package com.example.drugstore.activities


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.drugstore.R
import com.example.drugstore.databinding.ActivityHomeBinding
import com.example.drugstore.fragment.*
import com.example.drugstore.fragment.home.HomeFragment
import com.example.drugstore.fragment.order.OrderFragment
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        binding.btnNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navBtnHome -> {
                    replaceFragment(HomeFragment())
                }
                R.id.navBtnOrder -> {
                    replaceFragment(OrderFragment())
                }
                R.id.navBtnNotify -> {
                    replaceFragment(NotificationFragment())
                }
                else -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentBottomNav.id,fragment)
        fragmentTransaction.commit()
    }
}