package com.example.drugstore.presentation.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.drugstore.R
import com.example.drugstore.databinding.ActivityPrescriptionBinding
import com.example.drugstore.databinding.FragmentRemindDrugBinding
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrescriptionActivity : BaseActivity() {
    private var _binding: ActivityPrescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPrescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(NewPrescriptionFragment())
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentLayout.id, fragment)
        fragmentTransaction.commit()
    }
}