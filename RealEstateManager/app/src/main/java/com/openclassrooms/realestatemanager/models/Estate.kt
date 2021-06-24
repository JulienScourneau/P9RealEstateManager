package com.openclassrooms.realestatemanager.models

class Estate(
    var photos: List<Int>,
    var category: String,
    var price: String,
    var description: String,
    var surface: Int,
    var room: Int,
    var bathrooms: Int,
    var bedrooms: Int,
    var address: Address
)
