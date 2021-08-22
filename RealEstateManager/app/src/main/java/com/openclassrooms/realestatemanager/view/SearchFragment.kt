package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSearchEstateBinding
import com.openclassrooms.realestatemanager.viewmodel.SearchViewModel

class SearchFragment : Fragment(R.layout.fragment_search_estate) {

    private lateinit var binding: FragmentSearchEstateBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSearchEstateBinding.bind(view)

        setupEditText()

    }

    private fun setupEditText() {
        binding.apply {
            viewModel.apply {
                editTextMinimumPrice.addTextChangedListener {
                    searchMinPrice = it.toString()
                }
                editTextMaximumPrice.addTextChangedListener {
                    searchMaxPrice = it.toString()
                }
                editTextMinimumArea.addTextChangedListener {
                    searchMinArea = it.toString()
                }
                editTextMaximumArea.addTextChangedListener {
                    searchMaxArea = it.toString()
                }
                editTextMinimumBedroom.addTextChangedListener {
                    searchMinBedroom = it.toString()
                }
                editTextMaximumBedroom.addTextChangedListener {
                    searchMaxBedroom = it.toString()
                }
                editTextMinimumBathroom.addTextChangedListener {
                    searchMinBathroom = it.toString()
                }
                editTextMaximumBathroom.addTextChangedListener {
                    searchMaxBathroom = it.toString()
                }
                editTextCity.addTextChangedListener {
                    searchCity = it.toString()
                }
                editTextPostalCode.addTextChangedListener {
                    searchPostalCode = it.toString()
                }
            }
        }
    }
}