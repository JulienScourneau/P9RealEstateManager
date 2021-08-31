package com.openclassrooms.realestatemanager.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.Estate
import com.openclassrooms.realestatemanager.data.Search
import com.openclassrooms.realestatemanager.repository.EstateRepository
import com.openclassrooms.realestatemanager.utils.ADD_ESTATE_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: EstateRepository
) : ViewModel() {

    private fun searchEstate(search: Search) = viewModelScope.launch {
        repository.searchEstate(search)
    }

    private val searchEstateChannel = Channel<SearchViewModel.SearchEvent>()
    val searchEvent = searchEstateChannel.receiveAsFlow()

    private fun navigationBack() = viewModelScope.launch {
        searchEstateChannel.send(SearchViewModel.SearchEvent.NavigateBackWithResult)
    }

    fun onSearchClick() {
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
        searchEstate(search)
        Log.d("onSearchClick", "search: $search")
        navigationBack()
    }

    var searchCategory: String? = null
    var searchMinPrice: Int? = null
    var searchMaxPrice: Int? = null
    var searchMinArea: Int? = null
    var searchMaxArea: Int? = null
    var searchMinBedroom: Int? = null
    var searchMaxBedroom: Int? = null
    var searchMinBathroom: Int? = null
    var searchMaxBathroom: Int? = null
    var searchCity = ""
    var searchPostalCode = ""
    var searchPhotoAvailable = false
    var searchSchool: Boolean? = null
    var searchLocalCommerce: Boolean? = null
    var searchPark: Boolean? = null
    var searchPublicTransport: Boolean? = null
    var searchDate: Long? = null

    sealed class SearchEvent {
        object NavigateBackWithResult : SearchEvent()
    }
}