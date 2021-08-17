package com.openclassrooms.realestatemanager.data

import java.io.Serializable

data class Address(
    var number: String,
    var street: String,
    var city: String,
    var country: String,
    var postalCode: String
) : Serializable