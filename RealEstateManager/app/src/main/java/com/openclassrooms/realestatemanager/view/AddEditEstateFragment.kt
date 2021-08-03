package com.openclassrooms.realestatemanager.view

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentAddEditEstateBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.view.adapter.MediaAdapter
import com.openclassrooms.realestatemanager.viewmodel.AddEditEstateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.io.File

@AndroidEntryPoint
class AddEditEstateFragment : Fragment(R.layout.fragment_add_edit_estate) {

    private val viewModel: AddEditEstateViewModel by viewModels()
    private var images: ArrayList<Uri> = ArrayList()
    private lateinit var binding: FragmentAddEditEstateBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddEditEstateBinding.bind(view)

        setupUI()

        lifecycleScope.launchWhenStarted {
            viewModel.addEditEstateEvent.collect { event ->
                when (event) {
                    is AddEditEstateViewModel.AddEditEstateEvent.NavigationBackWithResult -> {
                        clearFocusOnSaveClick()
                        setFragmentResult(
                            "add_edit_request",
                            bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                    is AddEditEstateViewModel.AddEditEstateEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setupUI() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_spinner,
            android.R.layout.simple_spinner_dropdown_item
        )
        addTextChangedListener()
        setupPagerAdapter()

        binding.apply {

            categorySpinner.adapter = adapter
            categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapter: AdapterView<*>?, view: View?,
                    position: Int, id: Long
                ) {
                    viewModel.estateCategory = adapter?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
            categorySpinner.setSelection(Utils.getIndex(categorySpinner, viewModel.estateCategory))
            priceEditText.setText(viewModel.estatePrice)
            streetEditText.setText(viewModel.estateAddressStreet)
            numberEditText.setText(viewModel.estateAddressNumber)
            cityEditText.setText(viewModel.estateAddressCity)
            countryEditText.setText(viewModel.estateAddressCountry)
            postalCodeEditText.setText(viewModel.estateAddressPostalCode)
            areaEditText.setText(viewModel.estateArea)
            roomEditText.setText(viewModel.estateRoom)
            bathroomEditText.setText(viewModel.estateBathroom)
            bedroomEditText.setText(viewModel.estateBedroom)
            descriptionEditText.setText(viewModel.estateDescription)
            images.clear()
            for (item in viewModel.estatePhoto.indices){
                images.add(Uri.parse(viewModel.estatePhoto[item].photoReference))
                addEditViewpager.adapter?.notifyDataSetChanged()
            }

            addEditFab.setOnClickListener {
                viewModel.onSaveClick()
            }

            val photoFile: File = File.createTempFile(
                "IMG_", ".jpg",
                requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            )

            val imageUri: Uri = FileProvider.getUriForFile(
                requireContext(), "${requireContext().packageName}.provider",
                photoFile
            )

            val pickImages =
                registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                    images.add(uri)
                    addEditViewpager.adapter?.notifyDataSetChanged()
                }

            val takePicture =
                registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                    images.add(imageUri)
                    addEditViewpager.adapter?.notifyDataSetChanged()
                }

            val requestImagesPermissions =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                    if (isGranted) {
                        pickImages.launch("image/*")
                    }
                }

            val requestCameraPermissionsLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                    if (isGranted) {
                        takePicture.launch(imageUri)
                    }
                }

            addImageGalleryButton.setOnClickListener {
                requestImagesPermissions.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            addPicturePhotoButton.setOnClickListener {
                requestCameraPermissionsLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun setupPagerAdapter() {
        val mediaAdapter = MediaAdapter(requireContext(), images, false)
        binding.addEditViewpager.adapter = mediaAdapter
        setHasOptionsMenu(true)
    }

    private fun clearFocusOnSaveClick() {
        binding.apply {
            priceEditText.clearFocus()
            streetEditText.clearFocus()
            numberEditText.clearFocus()
            cityEditText.clearFocus()
            countryEditText.clearFocus()
            postalCodeEditText.clearFocus()
            areaEditText.clearFocus()
            roomEditText.clearFocus()
            bathroomEditText.clearFocus()
            bedroomEditText.clearFocus()
            descriptionEditText.clearFocus()
        }
    }

    private fun addTextChangedListener() {
        binding.apply {
            priceEditText.addTextChangedListener {
                viewModel.estatePrice = it.toString()
            }
            streetEditText.addTextChangedListener {
                viewModel.estateAddressStreet = it.toString()
            }
            numberEditText.addTextChangedListener {
                viewModel.estateAddressNumber = it.toString()
            }
            cityEditText.addTextChangedListener {
                viewModel.estateAddressCity = it.toString()
            }
            countryEditText.addTextChangedListener {
                viewModel.estateAddressCountry = it.toString()
            }
            postalCodeEditText.addTextChangedListener {
                viewModel.estateAddressPostalCode = it.toString()
            }
            areaEditText.addTextChangedListener {
                viewModel.estateArea = it.toString()
            }
            roomEditText.addTextChangedListener {
                viewModel.estateRoom = it.toString()
            }
            bathroomEditText.addTextChangedListener {
                viewModel.estateBathroom = it.toString()
            }
            bedroomEditText.addTextChangedListener {
                viewModel.estateBedroom = it.toString()
            }
            descriptionEditText.addTextChangedListener {
                viewModel.estateDescription = it.toString()
            }
        }
    }
}