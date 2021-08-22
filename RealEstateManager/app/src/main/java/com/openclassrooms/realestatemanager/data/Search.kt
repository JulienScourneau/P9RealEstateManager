package com.openclassrooms.realestatemanager.data

data class Search(
    var category: String?,
    var minPrice: String?,
    var maxPrice: String?,
    var minArea: String?,
    var maxArea: String?,
    var minBedroom: String?,
    var maxBedroom: String?,
    var minBathroom: String?,
    var maxBathroom: String?,
    var photoAvailable: Boolean?,
    var city: String?,
    var postalCode: String?,
    var school: Boolean?,
    var localCommerce: Boolean?,
    var publicTransport: Boolean?,
    var park: Boolean?,
    var date: Long?
    )
