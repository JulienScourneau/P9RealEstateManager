package com.openclassrooms.realestatemanager.utils

import com.openclassrooms.realestatemanager.data.Address
import com.openclassrooms.realestatemanager.data.Estate
import com.openclassrooms.realestatemanager.data.PointOfInterest
import com.openclassrooms.realestatemanager.models.RealEstateAgent

object TestList {

    private val address =
        Address("100", "Fake Street", "Fake City", "Fake Country", "123456")
    private val address1 =
        Address("100", "New Street", "New City", "New Country", "654321")
    private val address2 =
        Address("100", "Super Street", "Super City", "Super Country", "456123")

    private val pointOfInterest0 =
        PointOfInterest(school = true, localCommerce = false, publicTransport = true, park = false)
    private val pointOfInterest1 =
        PointOfInterest(school = false, localCommerce = true, publicTransport = false, park = true)
    private val pointOfInterest2 =
        PointOfInterest(school = true, localCommerce = false, publicTransport = false, park = true)

    private val estate =
        Estate(
            "Appartement", "700000", "Fake Description", "600", "8", "1", "2", false,RealEstateAgent("Adelajda Rutkowska", "0561049730"),
            pointOfInterest0, address, 1
        )
    private val estate1 =
        Estate(
            "Maison", "800000", "Fake_Description", "800", "9", "2", "3", true,RealEstateAgent("Sandra Eberhardt", "0548872315"),
            pointOfInterest1, address1, 2
        )
    private val estate2 =
        Estate(
            "Villa", "707000", "Fake Description", "600", "8", "1", "2", false,RealEstateAgent("Rebecca Z. McCoy", "0416869852"),
            pointOfInterest2, address2, 3
        )

    private val contactList = mutableListOf(
        RealEstateAgent("Sandra Eberhardt", "0548872315"),
        RealEstateAgent("Adelajda Rutkowska", "0561049730"),
        RealEstateAgent("Rebecca Z. McCoy", "0416869852")

    )

    val getContactList: ArrayList<RealEstateAgent>
        get() = ArrayList(contactList)

    val getEstate: Estate
        get() = estate

    val getEstate1: Estate
        get() = estate1

    val getEstate2: Estate
        get() = estate2
}



