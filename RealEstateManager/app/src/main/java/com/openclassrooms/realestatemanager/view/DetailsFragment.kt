package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.data.Photo
import com.openclassrooms.realestatemanager.databinding.FragmentDetailsEstateBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.view.adapter.MediaAdapter
import com.openclassrooms.realestatemanager.viewmodel.DetailsViewModel
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel
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
        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            resources.getString(R.string.toolbar_details)
        binding = FragmentDetailsEstateBinding.bind(view)

        viewModel.estateWithPhoto.observe(viewLifecycleOwner) {
            estate = it
            updateUI()
        }
        setFragmentResultListener("add_edit_request") { _, bundle ->
            val result = bundle.getInt("add_edit_result")
            viewModel.onEditResult(result, "Estate Updated")
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.estateEvent.collect { event ->
                when (event) {
                    is DetailsViewModel.DetailsEstateEvent.NavigateToEditEstateScreen -> {
                        val action =
                            DetailsFragmentDirections.actionDetailsFragmentToAddEditEstateFragment(
                                event.estate, resources.getString(R.string.toolbar_edit_estate)
                            )
                        findNavController().navigate(action)
                    }
                    is DetailsViewModel.DetailsEstateEvent.ShowEstateUpdatedConfirmationMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
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

    private fun updateUI() {
        setupTextView()
        setupMapView()
        binding.apply {
            estate.apply {
                detailsSchoolLayout.visibility =
                    if (estate.pointOfInterest.school) View.GONE else View.VISIBLE

                detailsLocalCommerceLayout.visibility =
                    if (estate.pointOfInterest.localCommerce) View.GONE else View.VISIBLE

                detailsPublicTransportLayout.visibility =
                    if (estate.pointOfInterest.publicTransport) View.GONE else View.VISIBLE

                detailsParkLayout.visibility =
                    if (estate.pointOfInterest.park) View.GONE else View.VISIBLE

                if (photosList.isNotEmpty()) {
                    images = photosList as ArrayList<Photo>
                } else {
                    val photo = Photo(
                        "android.resource://com.openclassrooms.realestatemanager/drawable/no_image_available",
                        0
                    )
                    images.add(photo)
                }
            }
            val mediaAdapter = MediaAdapter(this@DetailsFragment, images, true)
            detailsViewpager.adapter = mediaAdapter
            setHasOptionsMenu(true)
        }
    }

    private fun setupTextView() {
        binding.apply {
            estate.estate.apply {
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

    private fun setupMapView() {
        if (Utils.isInternetAvailable(requireContext())) {
            val latLng =
                Utils.getLocationFromAddress(Utils.formatAddress(estate.estate.address), context)
            if (latLng != null) {
                val url =
                    "https://maps.google.com/maps/api/staticmap?center=${latLng.latitude},${latLng.longitude}&zoom=16&size=200x200&scale=2&markers=color:red%7C${latLng.latitude},${latLng.longitude}&sensor=false&key=${BuildConfig.MAP_API_KEY}"
                Glide.with(binding.detailsMapImageView)
                    .load(url)
                    .apply(RequestOptions.centerInsideTransform())
                    .into(binding.detailsMapImageView)
                Log.d(
                    "setupMapView", "url: $url"
                )
            } else {
                binding.detailsMapImageView.setImageResource(R.drawable.ic_baseline_wrong_location_24)
            }

        } else {
            binding.detailsMapImageView.setImageResource(R.drawable.ic_baseline_wrong_location_24)
        }
    }

    override fun onItemClick(photo: Photo) {}
}