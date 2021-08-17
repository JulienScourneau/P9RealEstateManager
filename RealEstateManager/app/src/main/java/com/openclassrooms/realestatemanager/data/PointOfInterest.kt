package com.openclassrooms.realestatemanager.data

import java.io.Serializable

data class PointOfInterest(
    var school: Boolean = false,
    var localCommerce: Boolean = false,
    var publicTransport: Boolean = false,
    var park: Boolean = false
) : Serializable