package com.example.drugstore.presentation.user.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drugstore.databinding.FragmentAddressListBinding
import com.example.drugstore.presentation.adapter.AddressAdapter
import com.example.drugstore.presentation.adapter.AddressProfileAdapter
import com.example.drugstore.presentation.home.HomeActivity
import com.example.drugstore.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddressListFragment : Fragment() {
    private var _binding: FragmentAddressListBinding? = null
    val binding get() = _binding!!
    private lateinit var launcher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var viewModel: AddressListVM

    private lateinit var adapter: AddressProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressListBinding.inflate(inflater, container, false)
        val view = binding.root;
        observeViewModel()
        bindComponents()
        setUpLauncher()
        viewModel.fetchAddresses()
        return view
    }

    private fun setUpLauncher() {
        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.data!!.getBooleanExtra(Constants.ADDRESS, false)) {
                        viewModel.fetchAddresses()
                    }
                }
            }
    }

    private fun bindComponents() {
        binding.run {
            btnAdd.setOnClickListener {
                val intent = Intent(context, AddressInfoActivity::class.java)
                intent.putExtra(AddressInfoActivity.TYPE, AddressInfoActivity.ADD)
                launcher.launch(intent)
            }
            rvAddress.run {
                adapter = this@AddressListFragment.adapter
                layoutManager = LinearLayoutManager(context)
            }
            btnBack.setOnClickListener() {
                val intent = Intent(context, HomeActivity::class.java)
                intent.putExtra(HomeActivity.IS_PROFILE, true)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    private fun observeViewModel() {
        adapter = AddressProfileAdapter().apply {
            onEditClick = { address ->
                val intent = Intent(context, AddressInfoActivity::class.java)
                intent.putExtra(
                    AddressInfoActivity.TYPE,
                    AddressInfoActivity.EDIT,
                )
                intent.putExtra(AddressInfoActivity.ADDRESS_TITLE, address.title)
                launcher.launch(intent)
            }
            onDeleteClick = { address ->
                viewModel.removeAddress(address)
            }
        }

        viewModel.addresses.observe(viewLifecycleOwner) { addresses ->
            adapter.setList(addresses)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddressListFragment()
    }
}