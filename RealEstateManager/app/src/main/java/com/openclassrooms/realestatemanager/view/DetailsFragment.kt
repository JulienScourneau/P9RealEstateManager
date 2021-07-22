package com.openclassrooms.realestatemanager.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controller.AddEditEstate
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.databinding.EstateDetailsBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.view.adapter.MediaAdapter
import com.openclassrooms.realestatemanager.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import java.text.NumberFormat

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.estate_details) {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var mediaAdapter: MediaAdapter
    private var images: ArrayList<Int> = ArrayList()
    private lateinit var binding: EstateDetailsBinding
    private lateinit var estate: EstateWithPhoto

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = EstateDetailsBinding.bind(view)

        val args = this.arguments
        val id: Long = args?.get("estate_id") as Long

        viewModel.getEstateById(id).observe(viewLifecycleOwner) { estateWithPhoto ->
            if (estateWithPhoto != null) {
                updateUI(estateWithPhoto)
                estate = estateWithPhoto
            }
        }

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

                val estate = this.estate
                val intent = Intent(requireContext(), AddEditEstate::class.java)
                intent.putExtra("extra_object", estate)
                startActivity(intent)
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