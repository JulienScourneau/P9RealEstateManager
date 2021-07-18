package com.openclassrooms.realestatemanager.controller

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddEditEstateBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodel.AddEditEstateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddEditEstate : AppCompatActivity() {

    private val viewModel: AddEditEstateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = AddEditEstateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.categorySpinner
        ArrayAdapter.createFromResource(
            applicationContext, R.array.category_spinner, android.R.layout.simple_spinner_item
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

            addEditFab.setOnClickListener {
                viewModel.onSaveClick()
            }
        }
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
                        (bundleOf("add_edit_request" to event.result))
                        finish()
                    }
                    is AddEditEstateViewModel.AddEditEstateEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}