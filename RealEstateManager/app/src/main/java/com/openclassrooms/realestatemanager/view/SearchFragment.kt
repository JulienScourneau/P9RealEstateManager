package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSearchEstateBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel
import com.openclassrooms.realestatemanager.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search_estate) {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchEstateBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSearchEstateBinding.bind(view)
        setupUI()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchEvent.collect { event ->
                when (event) {
                    is SearchViewModel.SearchEvent.NavigateBackWithResult -> {
                        clearFocusOnSearchClick()

                        findNavController().popBackStack()
                    }
                }
            }

        }
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
        setupCheckBox()
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

                    addressText.text = Utils.setTextview(editTextCity, editTextPostalCode)
                }
                editTextPostalCode.addTextChangedListener {
                    searchPostalCode = it.toString()
                    addressText.text = Utils.setTextview(editTextCity, editTextPostalCode)

                    //if (editTextCity.text.isBlank()) {
                    //    addressText.text = it.toString()
                    //} else {
                    //    addressText.text = "${editTextCity.text},${it.toString()}"
                    //}
                    //if (it!!.isEmpty()) {
                    //    addressText.text = editTextCity.text
                    //}
                }
            }

            searchButton.setOnClickListener {
                viewModel.onSearchClick()
            }
        }
    }

    private fun setupCheckBox() {
        binding.photoCheckbox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.searchPhotoAvailable = isChecked
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
            Utils.expandAndCollapseView(expandAddress, layoutAddress, searchLayout)
        }
    }

    private fun clearFocusOnSearchClick() {
        binding.apply {
            editTextMinimumPrice.clearFocus()
            editTextMaximumPrice.clearFocus()
            editTextMinimumArea.clearFocus()
            editTextMaximumArea.clearFocus()
            editTextMinimumBedroom.clearFocus()
            editTextMaximumBedroom.clearFocus()
            editTextMinimumBathroom.clearFocus()
            editTextMaximumBathroom.clearFocus()
            editTextCity.clearFocus()
            editTextPostalCode.clearFocus()
        }
    }
}