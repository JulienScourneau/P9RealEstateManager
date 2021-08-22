package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.data.Search
import com.openclassrooms.realestatemanager.repository.EstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: EstateRepository
) : ViewModel() {

    private fun onSearchClick() {
        val search = Search(
            category = searchCategory,
            minPrice = searchMinPrice,
            maxPrice = searchMaxPrice,
            minArea = searchMinArea,
            maxArea = searchMaxArea,
            minBedroom = searchMinBedroom,
            maxBedroom = searchMaxBedroom,
            minBathroom = searchMinBathroom,
            maxBathroom = searchMaxBathroom,
            city = searchCity,
            postalCode = searchPostalCode,
            photoAvailable = searchPhotoAvailable,
            school = searchSchool,
            localCommerce = searchLocalCommerce,
            park = searchPark,
            publicTransport = searchPublicTransport,
            date = searchDate
        )
    }

    var searchCategory = ""
    var searchMinPrice = ""
    var searchMaxPrice = ""
    var searchMinArea = ""
    var searchMaxArea = ""
    var searchMinBedroom = ""
    var searchMaxBedroom = ""
    var searchMinBathroom = ""
    var searchMaxBathroom = ""
    var searchCity = ""
    var searchPostalCode = ""
    var searchPhotoAvailable = false
    var searchSchool: Boolean? = null
    var searchLocalCommerce: Boolean? = null
    var searchPark: Boolean? = null
    var searchPublicTransport: Boolean? = null
    var searchDate: Long? = null


}