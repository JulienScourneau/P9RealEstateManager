package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSearchEstateBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search_estate) {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchEstateBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSearchEstateBinding.bind(view)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            textviewSchool.text = indifferentSchool.text
            textviewLocalCommerce.text = indifferentLocalCommerce.text
            textviewPark.text = indifferentPark.text
            textviewPublicTransport.text = indifferentPublicTransport.text

            searchCategorySpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        adapter: AdapterView<*>?, view: View?,
                        position: Int, id: Long
                    ) {
                        viewModel.searchCategory = adapter?.getItemAtPosition(position).toString()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
        }
        setupEditText()
        setupExpandView()
        setupRadioButton()
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

    private fun setupRadioButton() {
        binding.apply {
            radioGroupSchool.setOnCheckedChangeListener { radioGroup, id ->
                when (id) {
                    R.id.indifferent_school -> {
                        textviewSchool.text = indifferentSchool.text
                        viewModel.searchSchool = null
                    }
                    R.id.with_school -> {
                        textviewSchool.text = withSchool.text
                        viewModel.searchSchool = true
                    }
                    R.id.without_school -> {
                        textviewSchool.text = withoutSchool.text
                        viewModel.searchSchool = false
                    }
                }
            }
            radioGroupLocalCommerce.setOnCheckedChangeListener { _, id ->
                when (id) {
                    R.id.indifferent_local_commerce -> {
                        textviewLocalCommerce.text = indifferentLocalCommerce.text
                    }
                    R.id.with_local_commerce -> {
                        textviewLocalCommerce.text = withLocalCommerce.text
                    }
                    R.id.without_local_commerce -> {
                        textviewLocalCommerce.text = withoutLocalCommerce.text
                    }
                }
            }
            radioGroupPark.setOnCheckedChangeListener { _, id ->
                when (id) {
                    R.id.indifferent_park -> {
                        textviewPark.text = indifferentPark.text
                    }
                    R.id.with_park -> {
                        textviewPark.text = withPark.text
                    }
                    R.id.without_park -> {
                        textviewPark.text = withoutPark.text
                    }
                }
            }
            radioGroupPublicTransport.setOnCheckedChangeListener { _, id ->
                when (id) {
                    R.id.indifferent_public_transport -> {
                        textviewPublicTransport.text = indifferentPublicTransport.text
                    }
                    R.id.with_public_transport -> {
                        textviewPublicTransport.text = withPublicTransport.text
                    }
                    R.id.without_public_transport -> {
                        textviewPublicTransport.text = withoutPublicTransport.text
                    }
                }
            }
        }
    }

    private fun setupExpandView() {
        binding.apply {
            Utils.expandAndCollapseView(expandSchool, layoutSchool, searchLayout)
            Utils.expandAndCollapseView(expandLocalCommerce, layoutLocalCommerce, searchLayout)
            Utils.expandAndCollapseView(expandPark, layoutPark, searchLayout)
            Utils.expandAndCollapseView(expandPublicTransport, layoutPublicTransport, searchLayout)
        }
    }
}