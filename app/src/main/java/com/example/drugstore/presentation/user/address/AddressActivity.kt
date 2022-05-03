package com.example.drugstore.presentation.user.address

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.drugstore.R
import com.example.drugstore.databinding.ActivityAddressBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddressActivity : AppCompatActivity() {
    private var _binding: ActivityAddressBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: AddressListVM;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(AddressListFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment)
            .commit()
    }
}