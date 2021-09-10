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
import com.google.android.gms.maps.model.Marker
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.viewmodel.MapsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MapsFragment : Fragment(R.layout.fragment_maps), GoogleMap.OnMarkerClickListener {

    private val viewModel: MapsViewModel by viewModels()
    private var estateList: List<EstateWithPhoto> = ArrayList()
    private val callback = OnMapReadyCallback { googleMap ->


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