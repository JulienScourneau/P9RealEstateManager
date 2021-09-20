package com.openclassrooms.realestatemanager.data

import java.io.Serializable

data class RealEstateAgent(
    var name: String,
    var phoneNumber: String
) : Serializable {
    override fun toString(): String {
        return name
    }
}