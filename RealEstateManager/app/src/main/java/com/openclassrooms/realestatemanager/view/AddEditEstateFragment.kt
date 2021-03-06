package com.openclassrooms.realestatemanager.view

import android.Manifest
import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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
import com.openclassrooms.realestatemanager.data.Photo
import com.openclassrooms.realestatemanager.data.RealEstateAgent
import com.openclassrooms.realestatemanager.databinding.FragmentAddEditEstateBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.view.adapter.MediaAdapter
import com.openclassrooms.realestatemanager.viewmodel.AddEditEstateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AddEditEstateFragment : Fragment(R.layout.fragment_add_edit_estate),
    MediaAdapter.OnItemClickListener {

    private val viewModel: AddEditEstateViewModel by viewModels()
    private var images: ArrayList<Photo> = ArrayList()
    private lateinit var binding: FragmentAddEditEstateBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = viewModel.title
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
            R.layout.spinner_item
        )
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
            images.clear()
            images = viewModel.estatePhoto as ArrayList<Photo>
            setupPagerAdapter()
            addEditViewpager.adapter?.notifyDataSetChanged()

            addEditFab.setOnClickListener {
                viewModel.onSaveClick()
            }
            dateText.text = "Date: ${Utils.getTodayDate()}"
        }

        setupContactSpinner()
        setupPointOfInterest()
        setupCheckboxListener()
        setupAddPhotoListener()
        addTextChangedListener()
        setupEditText()
        setupDatePicker()
    }

    private fun setupContactSpinner() {
        val contactList = Utils.getRealEstateAgent()
        var contact: RealEstateAgent
        val adapter =
            ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                contactList
            )
        binding.apply {
            contactSpinner.adapter = adapter
            contactSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapter: AdapterView<*>?, view: View?,
                    position: Int, id: Long
                ) {
                    contact = adapter?.getItemAtPosition(position) as RealEstateAgent
                    viewModel.estateContactName = contact.name
                    viewModel.estateContactPhoneNumber = contact.phoneNumber
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun setupAddPhotoListener() {
        binding.apply {

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
                    if (uri != null) {
                        binding.addEditViewpager.visibility = View.VISIBLE
                        images.add(Utils.convertUriToPhoto(uri))
                        viewModel.estatePhoto = images
                        addEditViewpager.adapter?.notifyDataSetChanged()
                    }
                }

            val takePicture =
                registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                    if (success) {
                        if (images.isEmpty())
                            binding.addEditViewpager.visibility = View.VISIBLE
                        images.add(Utils.convertUriToPhoto(imageUri))
                        viewModel.estatePhoto = images
                        addEditViewpager.adapter?.notifyDataSetChanged()
                    }
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

    private fun setupEditText() {
        binding.apply {
            categorySpinner.setSelection(
                Utils.getCategoryIndex(categorySpinner, viewModel.estateCategory)
            )
            contactSpinner.setSelection(
                Utils.getContactIndex(contactSpinner, viewModel.estateWithPhoto?.estate?.contact)
            )
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

        }
    }

    private fun setupPagerAdapter() {
        if (viewModel.estatePhoto.isNotEmpty())
            binding.addEditViewpager.visibility = View.VISIBLE
        val mediaAdapter = MediaAdapter(this, viewModel.estatePhoto, false)
        binding.addEditViewpager.adapter = mediaAdapter
        setHasOptionsMenu(true)
    }

    private fun setupPointOfInterest() {
        binding.apply {
            checkboxSchool.isChecked = viewModel.estateSchool
            checkboxLocalCommerce.isChecked = viewModel.estateLocalCommerce
            checkboxPublicTransport.isChecked = viewModel.estatePublicTransport
            checkboxPark.isChecked = viewModel.estatePark
        }
    }

    private fun setupDatePicker() {
        binding.apply {
            checkboxIsSold.isChecked = viewModel.estateIsSold
            checkboxIsSold.jumpDrawablesToCurrentState()
            checkboxIsSold.setOnCheckedChangeListener { _, isChecked ->
                viewModel.estateIsSold = isChecked
            }
            updateDateButton.setOnClickListener {

                val c = Calendar.getInstance()
                val y = c.get(Calendar.YEAR)
                val m = c.get(Calendar.MONTH)
                val d = c.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog(requireContext(), { _, year, month, day ->
                    val format = "dd/MM/yyyy"
                    val dateFormat = SimpleDateFormat(format, Locale.FRENCH)
                    val date = Calendar.getInstance()
                    date.set(year, month, day)
                    viewModel.estateDate = date.timeInMillis
                    binding.dateText.text = "Date: ${dateFormat.format(date.time)}"
                }, y, m, d)
                dpd.show()
            }
        }
    }

    override fun onItemClick(photo: Photo) {
        images.remove(photo)
        viewModel.estatePhoto = images
        binding.addEditViewpager.adapter?.notifyDataSetChanged()
        if (images.isEmpty())
            binding.addEditViewpager.visibility = View.GONE
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
            viewModel.apply {
                priceEditText.addTextChangedListener {
                    estatePrice = it.toString()
                }
                streetEditText.addTextChangedListener {
                    estateAddressStreet = it.toString()
                }
                numberEditText.addTextChangedListener {
                    estateAddressNumber = it.toString()
                }
                cityEditText.addTextChangedListener {
                    estateAddressCity = it.toString()
                }
                countryEditText.addTextChangedListener {
                    estateAddressCountry = it.toString()
                }
                postalCodeEditText.addTextChangedListener {
                    estateAddressPostalCode = it.toString()
                }
                areaEditText.addTextChangedListener {
                    estateArea = it.toString()
                }
                roomEditText.addTextChangedListener {
                    estateRoom = it.toString()
                }
                bathroomEditText.addTextChangedListener {
                    estateBathroom = it.toString()
                }
                bedroomEditText.addTextChangedListener {
                    estateBedroom = it.toString()
                }
                descriptionEditText.addTextChangedListener {
                    estateDescription = it.toString()
                }
            }
        }
    }

    private fun setupCheckboxListener() {
        binding.apply {
            checkboxSchool.setOnCheckedChangeListener { _, isChecked ->
                viewModel.estateSchool = isChecked
            }
            checkboxLocalCommerce.setOnCheckedChangeListener { _, isChecked ->
                viewModel.estateLocalCommerce = isChecked
            }
            checkboxPublicTransport.setOnCheckedChangeListener { _, isChecked ->
                viewModel.estatePublicTransport = isChecked
            }
            checkboxPark.setOnCheckedChangeListener { _, isChecked ->
                viewModel.estatePark = isChecked
            }
        }
    }
}