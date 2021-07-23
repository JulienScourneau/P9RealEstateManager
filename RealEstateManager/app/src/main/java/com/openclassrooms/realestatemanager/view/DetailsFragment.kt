package com.openclassrooms.realestatemanager.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.databinding.FragmentDetailsEstateBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.view.adapter.MediaAdapter
import com.openclassrooms.realestatemanager.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details_estate) {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var mediaAdapter: MediaAdapter
    private var images: ArrayList<Int> = ArrayList()
    private lateinit var binding: FragmentDetailsEstateBinding
    private lateinit var estate: EstateWithPhoto

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentDetailsEstateBinding.bind(view)

        //if (arguments != null){
        //    val args = this.arguments
        //    val id: Long = args?.get("estate_id") as Long
        //
        //    viewModel.getEstateById(id).observe(viewLifecycleOwner) { estateWithPhoto ->
        //        if (estateWithPhoto != null) {
        //            updateUI(estateWithPhoto)
        //            estate = estateWithPhoto
        //        }
        //    }
        //}

        mediaAdapter = MediaAdapter(requireContext(), images)
        setupViewPager()
        binding.viewpager.adapter = mediaAdapter
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit_estate -> {
                val action = DetailsFragmentDirections.actionDetailsFragmentToAddEditEstateFragment(null)
                findNavController().navigate(action)
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

        images.add(R.drawable.house)
        images.add(R.drawable.kitchen)
        images.add(R.drawable.bathroom)
        images.add(R.drawable.bedroom)
        images.add(R.drawable.living_room)
    }

}