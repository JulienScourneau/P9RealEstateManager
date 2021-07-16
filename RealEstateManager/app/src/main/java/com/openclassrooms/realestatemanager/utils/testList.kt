package com.openclassrooms.realestatemanager.utils

import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.Address
import com.openclassrooms.realestatemanager.data.Estate
import com.openclassrooms.realestatemanager.data.Photo
import kotlin.collections.ArrayList


object TestList {
    private val photosApartment = mutableListOf(
        R.drawable.apartment,
        R.drawable.living_room,
        R.drawable.kitchen,
        R.drawable.bathroom,
        R.drawable.bedroom
    )

    private val photosHouse = mutableListOf(
        Photo(R.drawable.house.toString(), 0),
        Photo(R.drawable.living_room.toString(), 0),
        Photo(R.drawable.kitchen.toString(), 0)
    )

    private val address =
        Address("100", "Fake Street", "Fake City", "Fake Country", "123456")
    private val address1 =
        Address("100", "New Street", "New City", "New Country", "654321")
    private val address2 =
        Address("100", "Super Street", "Super City", "Super Country", "456123")

    private val estate =
        Estate("Fake_category 1", "700 000 €", "Fake Description", "600", "8", "1", "2", address, 0)
    private val estate1 =
        Estate("Fake Category 2", "800 000 €", "Fake_Description", "800", "9", "2", "3", address1, 1)
    private val estate2 =
        Estate("Fake_category 3", "707 000 €", "Fake Description", "600", "8", "1", "2", address2, 2)
    //Estate("Fake Category 4", "990 000 €", "Fake_Description", 800, 9, 2, 3, address),
    //Estate("Fake_category 5", "550 000 €", "Fake Description", 600, 8, 1, 2, address),
    //Estate("Fake Category 6", "650 000 €", "Fake_Description", 800, 9, 2, 3, address),
    //Estate("Fake_category 7", "500 000 €", "Fake Description", 600, 8, 1, 2, address),
    //Estate("Fake Category 8", "900 000 €", "Fake_Description", 800, 9, 2, 3, address

    //)
    //val getTestList: ArrayList<Estate>
    //    get() = ArrayList(estateList)

    val getEstate: Estate
        get() = estate

    val getEstate1: Estate
        get() = estate1

    val getEstate2: Estate
        get() = estate2

    val getApartmentPhoto: ArrayList<Int>
        get() = ArrayList(photosApartment)

    val getHousePhoto: MutableList<Photo>
        get() = photosHouse

}



