package com.openclassrooms.realestatemanager.view

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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.BuildConfig
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
import java.util.*

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

    private fun updateUI(estateWithPhoto: EstateWithPhoto) {
        setupTextView(estateWithPhoto.estate)
        setupMapView(estateWithPhoto.estate)
        binding.apply {

            detailsSchoolLayout.visibility =
                if (!estateWithPhoto.estate.pointOfInterest.school) View.GONE else View.VISIBLE

            detailsLocalCommerceLayout.visibility =
                if (!estateWithPhoto.estate.pointOfInterest.localCommerce) View.GONE else View.VISIBLE

            detailsPublicTransportLayout.visibility =
                if (!estateWithPhoto.estate.pointOfInterest.publicTransport) View.GONE else View.VISIBLE

            detailsParkLayout.visibility =
                if (!estateWithPhoto.estate.pointOfInterest.park) View.GONE else View.VISIBLE

            if (estateWithPhoto.photosList.isNotEmpty()) {
                images = estateWithPhoto.photosList as ArrayList<Photo>
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
                if (isSold) {
                    detailsSoldText.text = "Vendu depuis:"
                    detailsSoldData.text = Utils.convertLongToDate(date)
                } else {
                    detailsSoldText.text = "En vente depuis:"
                    detailsSoldData.text = Utils.convertLongToDate(date)
                }
                detailsPrice.text = if (price.isNotBlank()) Utils.formatPrice(price) else "N/A"
                detailsAreaData.text = if (area.isNotBlank()) area else "N/A"
                detailsRoomData.text = if (room.isNotBlank()) room else "N/A"
                detailsBathroomData.text = if (bathroom.isNotBlank()) bathroom else "N/A"
                detailsBedroomData.text = if (bedroom.isNotBlank()) bedroom else "N/A"
                detailsDescriptionText.text = if (description.isNotBlank()) description else "N/A"
            }
        }
    }

    private fun setupMapView(estate: Estate) {
        val latLng = Utils.getLocationFromAddress(Utils.formatAddress(estate.address), context)
        if (latLng != null) {
            val url =
                "https://maps.google.com/maps/api/staticmap?center=${latLng.latitude},${latLng.longitude}&zoom=16&size=200x200&scale=2&markers=color:red%7C${latLng.latitude},${latLng.longitude}&sensor=false&key=${BuildConfig.MAP_API_KEY}"
            Glide.with(binding.detailsMapImageView)
                .load(url)
                .apply(RequestOptions.centerInsideTransform())
                .into(binding.detailsMapImageView)
            Log.d(
                "updateUI", "url: $url"
            )
        } else {
            binding.detailsMapImageView.setImageResource(R.drawable.ic_baseline_wrong_location_24)
        }
    }

    override fun onItemClick(photo: Photo) {}
}