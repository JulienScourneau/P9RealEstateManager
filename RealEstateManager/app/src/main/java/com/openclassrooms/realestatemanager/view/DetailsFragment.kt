package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.Estate
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.data.Photo
import com.openclassrooms.realestatemanager.databinding.FragmentDetailsEstateBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.view.adapter.MediaAdapter
import com.openclassrooms.realestatemanager.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.ArrayList

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details_estate),
    MediaAdapter.OnItemClickListener {

    private val viewModel: DetailsViewModel by viewModels()
    private var images: ArrayList<Photo> = ArrayList()
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
        setupTextView(estate.estate)
        binding.apply {

            if (!estate.estate.pointOfInterest.school) {
                detailsSchoolLayout.visibility = View.GONE
            }
            if (!estate.estate.pointOfInterest.localCommerce) {
                detailsLocalCommerceLayout.visibility = View.GONE
            }
            if (!estate.estate.pointOfInterest.publicTransport) {
                detailsPublicTransportLayout.visibility = View.GONE
            }
            if (!estate.estate.pointOfInterest.park) {
                detailsParkLayout.visibility = View.GONE
            }
            if (estate.photosList.isNotEmpty()) {
                images = estate.photosList as ArrayList<Photo>
            } else {
                val photo = Photo(
                    "android.resource://com.openclassrooms.realestatemanager/drawable/no_image_available",
                    0
                )
                images.add(photo)
            }
            val mediaAdapter = MediaAdapter(this@DetailsFragment, images, true)
            detailsViewpager.adapter = mediaAdapter
            setHasOptionsMenu(true)
        }
    }

    private fun setupTextView(estate: Estate) {
        binding.apply {
            estate.apply {
                detailsCategory.text = category
                detailsLocationData.text = Utils.formatAddress(address)
                detailsContactNameData.text = contact.name
                detailsContactPhoneNumberData.text = contact.phoneNumber
                detailsPrice.text = if (price.isBlank()) price else "N/A"
                detailsAreaData.text = if (area.isBlank()) area else "N/A"
                detailsRoomData.text = if (room.isBlank()) room else "N/A"
                detailsBathroomData.text = if (bathroom.isBlank()) bathroom else "N/A"
                detailsBedroomData.text = if (bedroom.isBlank()) bedroom else "N/A"
                detailsDescriptionText.text = if (description.isBlank()) description else "N/A"
            }
        }
    }


    override fun onItemClick(photo: Photo) {}
}