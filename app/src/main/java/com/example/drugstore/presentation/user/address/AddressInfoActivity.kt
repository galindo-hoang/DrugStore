package com.example.drugstore.presentation.user.address

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.drugstore.databinding.ActivityAddressInfoBinding

class AddressInfoActivity : AppCompatActivity() {
    private var _binding: ActivityAddressInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddressInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(AddressMapFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}