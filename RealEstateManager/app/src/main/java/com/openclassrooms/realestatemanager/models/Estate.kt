package com.openclassrooms.realestatemanager.models

data class Estate(
    var photos: List<Int>,
    var category: String,
    var price: String,
    var description: String,
    var area: Int,
    var room: Int,
    var bathrooms: Int,
    var bedrooms: Int,
    var address: Address
)
