package com.example.drugstore.presentation.user.address

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drugstore.databinding.FragmentAddressListBinding
import com.example.drugstore.presentation.adapter.AddressAdapter
import com.example.drugstore.presentation.adapter.AddressProfileAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddressListFragment : Fragment() {
    private var _binding: FragmentAddressListBinding? = null
    val binding get() = _binding!!

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
        bindComponents();
        return view;
    }

    private fun bindComponents() {
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(context, AddressInfoActivity::class.java))
        }
    }

    private fun observeViewModel() {
        adapter = AddressProfileAdapter().apply {
            onEditClick = { address ->

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