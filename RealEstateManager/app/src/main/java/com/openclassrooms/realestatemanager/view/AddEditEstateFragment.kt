package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.databinding.FragmentAddEditEstateBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodel.AddEditEstateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddEditEstateFragment : Fragment(R.layout.fragment_add_edit_estate) {

    private val viewModel: AddEditEstateViewModel by viewModels()
    private lateinit var binding: FragmentAddEditEstateBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddEditEstateBinding.inflate(layoutInflater)

        setUpUI()
        addTextChangedListener()

        lifecycleScope.launchWhenStarted {
            viewModel.addEditEstateEvent.collect { event ->
                when (event) {
                    is AddEditEstateViewModel.AddEditEstateEvent.NavigationBackWithResult -> {
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
                    is AddEditEstateViewModel.AddEditEstateEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setUpUI() {
        val spinner: Spinner = binding.categorySpinner
        ArrayAdapter.createFromResource(
            requireContext(), R.array.category_spinner, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.apply {
            categorySpinner.setSelection(Utils.getIndex(spinner, viewModel.estateCategory))
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

            categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel.estateCategory = p0?.getItemAtPosition(p2).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            addTextChangedListener()

            addEditFab.setOnClickListener {
                viewModel.onSaveClick()
            }
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