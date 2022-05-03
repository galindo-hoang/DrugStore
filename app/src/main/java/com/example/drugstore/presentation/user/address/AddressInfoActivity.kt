package com.example.drugstore.presentation.user.address

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.drugstore.databinding.ActivityAddressInfoBinding
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddressInfoActivity : AppCompatActivity() {
    private var _binding: ActivityAddressInfoBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var addressListVM: AddressListVM
    lateinit var type: String
    lateinit var mapFragment: AddressMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAddressInfoBinding.inflate(layoutInflater)
        mapFragment = AddressMapFragment.newInstance(addressListVM)

        intent.getStringExtra(TYPE)?.let {
            type = it
            if (type == EDIT) {
                val addressTitle = intent.getStringExtra(ADDRESS_TITLE)
                binding.etTitle.run {
                    setText(addressTitle)
                    isFocusable = false
                }
                getAddress(addressTitle!!)
            }
        }
        setContentView(binding.root)

        bindComponents()

        replaceFragment(mapFragment)
    }

    private fun getAddress(addressTitle: String) {
        addressListVM.getAddress(addressTitle, ::moveCamera)

        addressListVM.address.observe(this@AddressInfoActivity) {
            binding.etPhoneNumber.setText(it.phoneNumber)
        }
    }

    private fun moveCamera(lat: Double, long: Double) = mapFragment.moveCamera(lat, long)

    private fun bindComponents() {
        binding.run {
            btnConfirm.setOnClickListener {
                addressListVM.onConfirm(
                    etTitle.text.toString(),
                    etPhoneNumber.text.toString(),
                    type,
                    this@AddressInfoActivity::onSuccess
                )
            }
            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun onSuccess() {
        val resultIntent = Intent()
        resultIntent.putExtra(Constants.ADDRESS, true)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    companion object {
        const val TYPE = "TYPE"
        const val ADD = "ADD"
        const val EDIT = "EDIT"
        const val ADDRESS_TITLE = "TITLE"
    }
}