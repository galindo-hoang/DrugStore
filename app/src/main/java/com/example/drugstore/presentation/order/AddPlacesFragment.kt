package com.example.drugstore.presentation.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentAddPlacesBinding
import com.example.drugstore.utils.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class AddPlacesFragment : Fragment() {
    private lateinit var binding: FragmentAddPlacesBinding
    private lateinit var viewModel: MapVM
    private var curLatLng: LatLng? = null

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(curLatLng!!))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curLatLng!!, 15f))
//        googleMap.setOnCameraIdleListener {
//            val midLatLng: LatLng = googleMap.cameraPosition.target
//            viewModel.selectItem(midLatLng)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        curLatLng = LatLng(
            requireArguments().getDouble(Constants.LATITUDE),
            requireArguments().getDouble(Constants.LONGITUDE)
        )
        binding = FragmentAddPlacesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get()
        binding.ivCurrent.setOnClickListener {
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}