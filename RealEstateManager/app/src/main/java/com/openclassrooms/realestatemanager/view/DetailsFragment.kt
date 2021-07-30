package com.openclassrooms.realestatemanager.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.databinding.FragmentDetailsEstateBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.view.adapter.MediaAdapter
import com.openclassrooms.realestatemanager.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details_estate) {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var mediaAdapter: MediaAdapter
    private var images: ArrayList<Uri> = ArrayList()
    private lateinit var binding: FragmentDetailsEstateBinding
    private lateinit var estate: EstateWithPhoto

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentDetailsEstateBinding.bind(view)

        viewModel.estateId?.let {
            viewModel.getEstateById(it).observe(viewLifecycleOwner) { estateWithPhoto ->
                if (estateWithPhoto != null) {
                    updateUI(estateWithPhoto)
                    estate = estateWithPhoto
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.estateEvent.collect { event ->
                when (event) {
                    is DetailsViewModel.DetailsEstateEvent.NavigateToEditEstateScreen -> {
                        val action =
                            DetailsFragmentDirections.actionDetailsFragmentToAddEditEstateFragment(
                                event.estate
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        }
        setupViewPager()
        mediaAdapter = MediaAdapter(requireContext(), images)
        binding.detailsViewpager.adapter = mediaAdapter
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit_estate -> {
                viewModel.onEditEstateSelected(estate)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI(estate: EstateWithPhoto) {
        binding.apply {
            detailsCategory.text = estate.estate.category
            detailsLocation.text = estate.estate.address.city
            detailsPrice.text = Utils.formatPrice(estate.estate.price)
            detailsAreaData.text = estate.estate.area
            detailsRoomData.text = estate.estate.room
            detailsBathroomData.text = estate.estate.bathroom
            detailsBedroomData.text = estate.estate.bedroom
            detailsLocationData.text = Utils.formatAddress(estate.estate.address)
            detailsDescriptionText.text = estate.estate.description

        }
    }

    private fun setupViewPager() {
        val uri = Uri.parse("android.resources://com.openclassrooms.realestatemanager/drawable/house")
        images.add(uri)
        //images.add(Uri.parse(R.drawable.house.toString()))
        //images.add(Uri.parse(R.drawable.kitchen.toString()))
        //images.add(Uri.parse(R.drawable.bathroom.toString()))
        //images.add(Uri.parse(R.drawable.bedroom.toString()))
        //images.add(Uri.parse(R.drawable.living_room.toString()))
        Log.d("setupViewpager","uri: $uri")

    }
}