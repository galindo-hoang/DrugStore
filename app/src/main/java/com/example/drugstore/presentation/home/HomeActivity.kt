package com.example.drugstore.presentation.home


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drugstore.R
import com.example.drugstore.databinding.ActivityHomeBinding
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.notify.NotificationFragment
import com.example.drugstore.presentation.order.OrderFragment
import com.example.drugstore.presentation.user.NewPrescriptionFragment
import com.example.drugstore.presentation.user.ProfileFragment
import com.example.drugstore.presentation.user.UpdateProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val isNewUser = intent.getBooleanExtra("isNewUser", false)
        if (isNewUser) {
            replaceFragment(UpdateProfileFragment())
        } else {
            replaceFragment(HomeFragment())
        }
        binding.run {
            btnNavView.setOnItemSelectedListener { item ->
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
                    R.id.navBtnProfile -> {
                        replaceFragment(ProfileFragment())
                    }
                    else ->
                        Toast.makeText(
                            this@HomeActivity,
                            "Sorry. Some errors occur",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                }
                true
            }

            navBtnNewPrescription.setOnClickListener {
                replaceFragment(NewPrescriptionFragment())
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentBottomNav.id, fragment)
        fragmentTransaction.commit()
    }
}