package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.data.Search
import com.openclassrooms.realestatemanager.repository.EstateRepository
import com.openclassrooms.realestatemanager.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
) : ViewModel() {
    private val searchEstateChannel = Channel<SearchEvent>()
    val searchEvent = searchEstateChannel.receiveAsFlow()

    fun getSearchEstate(search: Search) = viewModelScope.launch {
        searchEstateChannel.send(SearchEvent.NavigateToListScreen(search))
    }

    fun onSearchClick(): Search {
        return Search(
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

    var searchCategory: String? = null
    var searchMinPrice: Int? = null
    var searchMaxPrice: Int? = null
    var searchMinArea: Int? = null
    var searchMaxArea: Int? = null
    var searchMinBedroom: Int? = null
    var searchMaxBedroom: Int? = null
    var searchMinBathroom: Int? = null
    var searchMaxBathroom: Int? = null
    var searchCity: String? = null
    var searchPostalCode: String? = null
    var searchPhotoAvailable = false
    var searchSchool: Boolean? = null
    var searchLocalCommerce: Boolean? = null
    var searchPark: Boolean? = null
    var searchPublicTransport: Boolean? = null
    var searchDate: Long? = null

    sealed class SearchEvent {
        data class NavigateToListScreen(val search: Search) : SearchEvent()
    }
}