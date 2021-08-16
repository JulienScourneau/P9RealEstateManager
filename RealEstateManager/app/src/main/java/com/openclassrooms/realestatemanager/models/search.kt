package com.openclassrooms.realestatemanager.models

data class search(
    var category: String,
    var minPrice: String,
    var maxPrice: String,
    var minArea: String,
    var maxArea: String,
    var room: String,
    var bedroom: String,
    var bathroom: String,
    var photoAvailable: Boolean,
    var city: String,
    var postalCode: String,
    var school: Boolean,
    var localCommerce: Boolean ,
    var publicTransport: Boolean ,
    var park: Boolean,
    var date: Long
    )
