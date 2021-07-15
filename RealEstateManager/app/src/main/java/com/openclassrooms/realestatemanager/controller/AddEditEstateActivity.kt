package com.openclassrooms.realestatemanager.controller

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddEditActivityEstateBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodel.AddEditEstateViewModel

class AddEditEstateActivity : AppCompatActivity() {

    private val viewModel: AddEditEstateViewModel by viewModels()

    private lateinit var binding: AddEditActivityEstateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AddEditActivityEstateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.categorySpinner
        ArrayAdapter.createFromResource(
            this, R.array.category_spinner, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.apply {
            //categorySpinner.setSelection(Utils.getIndex(spinner, viewModel.estateCategory))
            priceEditText.setText(viewModel.estatePrice)
            streetEditText.setText(viewModel.estateAddressStreet)
            numberEditText.setText(viewModel.estateAddressNumber.toString())
            cityEditText.setText(viewModel.estateAddressCity)
            countryEditText.setText(viewModel.estateAddressCountry)
            postalCodeEditText.setText(viewModel.estateAddressPostalCode.toString())
            areaEditText.setText(viewModel.estateArea.toString())
            roomEditText.setText(viewModel.estateRoom.toString())
            bathroomEditText.setText(viewModel.estateBathroom.toString())
            bedroomEditText.setText(viewModel.estateBedroom.toString())

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


        }
    }
}