package com.openclassrooms.realestatemanager.data

data class Search(
    var category: String?,
    var minPrice: Int?,
    var maxPrice: Int?,
    var minArea: Int?,
    var maxArea: Int?,
    var minBedroom: Int?,
    var maxBedroom: Int?,
    var minBathroom: Int?,
    var maxBathroom: Int?,
    var photoAvailable: Boolean?,
    var city: String?,
    var postalCode: String?,
    var school: Boolean?,
    var localCommerce: Boolean?,
    var publicTransport: Boolean?,
    var park: Boolean?,
    var date: Long?
    )
