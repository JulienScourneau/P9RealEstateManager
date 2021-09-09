package com.openclassrooms.realestatemanager.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSearchEstateBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

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
                    is SearchViewModel.SearchEvent.NavigateToListScreen -> {
                        clearFocusOnSearchClick()
                        val action = SearchFragmentDirections.actionSearchFragmentToListFragment(event.search)
                        findNavController().navigate(action)
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
            dateText.text = "A partir du: ${Utils.getTodayDate()}"

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
        setupDatePicker()
    }

    private fun setupEditText() {
        binding.apply {
            viewModel.apply {
                editTextMinimumPrice.addTextChangedListener {
                    if (!it.isNullOrBlank())
                        searchMinPrice = it.toString().toInt()
                }
                editTextMaximumPrice.addTextChangedListener {
                    if (!it.isNullOrBlank())
                        searchMaxPrice = it.toString().toInt()
                }
                editTextMinimumArea.addTextChangedListener {
                    if (!it.isNullOrBlank())
                        searchMinArea = it.toString().toInt()
                }
                editTextMaximumArea.addTextChangedListener {
                    if (!it.isNullOrBlank())
                        searchMaxArea = it.toString().toInt()
                }
                editTextMinimumBedroom.addTextChangedListener {
                    if (!it.isNullOrBlank())
                        searchMinBedroom = it.toString().toInt()
                }
                editTextMaximumBedroom.addTextChangedListener {
                    if (!it.isNullOrBlank())
                        searchMaxBedroom = it.toString().toInt()
                }
                editTextMinimumBathroom.addTextChangedListener {
                    if (!it.isNullOrBlank())
                        searchMinBathroom = it.toString().toInt()
                }
                editTextMaximumBathroom.addTextChangedListener {
                    if (!it.isNullOrBlank())
                        searchMaxBathroom = it.toString().toInt()
                }
                editTextCity.addTextChangedListener {
                    searchCity = it.toString()
                }
                editTextPostalCode.addTextChangedListener {
                    searchPostalCode = it.toString()
                }
            }

            searchButton.setOnClickListener {
               viewModel.getSearchEstate(viewModel.onSearchClick())
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
            radioGroupSchool.setOnCheckedChangeListener { _, id ->
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

    private fun setupDatePicker() {
        binding.apply {
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
                    viewModel.searchDate = date.timeInMillis
                    binding.dateText.text = "A partir du: ${dateFormat.format(date.time)}"
                }, y, m, d)
                dpd.show()
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