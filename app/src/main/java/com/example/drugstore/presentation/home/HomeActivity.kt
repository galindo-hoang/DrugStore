package com.example.drugstore.presentation.home


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drugstore.R
import com.example.drugstore.databinding.ActivityHomeBinding
import com.example.drugstore.presentation.notify.NotificationFragment
import com.example.drugstore.presentation.order.OrderFragment
import com.example.drugstore.presentation.user.ProfileFragment

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