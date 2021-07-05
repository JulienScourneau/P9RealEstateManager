package com.openclassrooms.realestatemanager.models

data class Address(
    var number: Int,
    var street: String,
    var city: String,
    var country: String,
    var postalCode: String
)
