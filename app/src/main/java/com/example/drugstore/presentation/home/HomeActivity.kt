package com.example.drugstore.presentation.home


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.drugstore.R
import com.example.drugstore.databinding.ActivityHomeBinding
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.notify.NotificationFragment
import com.example.drugstore.presentation.order.OrderFragment
import com.example.drugstore.presentation.user.reminder.PrescriptionActivity
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
        val isNewUser = intent.getBooleanExtra(IS_NEW_USER, false)
        val isProfile = intent.getBooleanExtra(IS_PROFILE, false)
        if (isNewUser) {
            replaceFragment(UpdateProfileFragment())
        } else if (isProfile) {
            replaceFragment(ProfileFragment())
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
                val intent = Intent(
                    this@HomeActivity,
                    PrescriptionActivity::class.java
                )
                startActivity(intent)
                finish()
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentBottomNav.id, fragment)
        fragmentTransaction.commit()
    }

    companion object {
        const val IS_NEW_USER = "isNewUser"
        const val IS_PROFILE = "profile"
    }
}