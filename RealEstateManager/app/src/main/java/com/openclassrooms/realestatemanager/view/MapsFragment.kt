package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodel.MapsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MapsFragment : Fragment(R.layout.fragment_maps), GoogleMap.OnMarkerClickListener {

    private val viewModel: MapsViewModel by viewModels()
    private var estateList: List<EstateWithPhoto> = ArrayList()
    private val callback = OnMapReadyCallback { googleMap ->

        for (i in estateList.indices) {
            val latLng = Utils.getLocationFromAddress(
                Utils.formatAddress(estateList[i].estate.address),
                requireContext()
            )
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng())
            //googleMap.animateCamera(CameraUpdateFactory.zoomTo(16f))
            if (latLng != null) {
                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker())
                markerOptions.snippet(estateList[i].estate.id.toString())
                markerOptions.title(estateList[i].estate.id.toString())
                googleMap.addMarker(markerOptions)
            }
        }
        googleMap.setOnMarkerClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allEstate.observe(viewLifecycleOwner) {
            estateList = it
            updateMap()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.mapsEvent.collect { event ->
                when (event) {
                    is MapsViewModel.MapsEvent.NavigateToDetailsScreen -> {
                        val action =
                            MapsFragmentDirections.actionMapsFragmentToDetailsFragment(event.id)
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    private fun updateMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onMarkerClick(marker: Marker): Boolean {

        return true
    }
}