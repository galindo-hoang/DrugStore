package com.example.drugstore.presentation.order

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.drugstore.R
import com.example.drugstore.databinding.FragmentAddPlacesBinding
import com.example.drugstore.utils.Constants

import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng

class AddPlacesFragment : Fragment() {
    private lateinit var binding: FragmentAddPlacesBinding
    private lateinit var viewModel: MapVM
    private var curLatLng: LatLng? = null

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(curLatLng!!))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curLatLng!!,15f))
        googleMap.setOnCameraIdleListener {
            val midLatLng: LatLng = googleMap.cameraPosition.target
            viewModel.selectItem(midLatLng)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        curLatLng = LatLng(requireArguments().getDouble(Constants.LATITUDE),requireArguments().getDouble(Constants.LONGITUDE))
        binding = FragmentAddPlacesBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(requireActivity()).get()
        binding.ivCurrent.setOnClickListener{
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
        }


//        Log.e("-fm","onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Log.e("-fm","onViewCreated")
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        Log.e("-fm","onAttach")
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.e("-fm","onCreate")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.e("-fm","onResume")
//    }
//
//    override fun onStart() {
//        super.onStart()
//        Log.e("-fm","onStart")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.e("-fm","onPause")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.e("-fm","onStop")
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        Log.e("-fm","onDetach")
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        Log.e("-fm","onDestroyView")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.e("-fm","onDestroy")
//    }
}