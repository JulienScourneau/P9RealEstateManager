package com.openclassrooms.realestatemanager.utils

import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.Address
import com.openclassrooms.realestatemanager.data.Estate
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
        R.drawable.house,
        R.drawable.living_room,
        R.drawable.kitchen,
        R.drawable.bathroom,
        R.drawable.bedroom
    )

    private val address =
        Address(100, "Fake Street", "Fake City", "Fake Country", 123456)

    private val estateList = mutableListOf(
        Estate("Fake_category 1", "700 000 €", "Fake Description", 600, 8, 1, 2, address),
        Estate("Fake Category 2", "800 000 €", "Fake_Description", 800, 9, 2, 3, address),
        Estate("Fake_category 3", "707 000 €", "Fake Description", 600, 8, 1, 2, address),
        Estate("Fake Category 4", "990 000 €", "Fake_Description", 800, 9, 2, 3, address),
        Estate("Fake_category 5", "550 000 €", "Fake Description", 600, 8, 1, 2, address),
        Estate("Fake Category 6", "650 000 €", "Fake_Description", 800, 9, 2, 3, address),
        Estate("Fake_category 7", "500 000 €", "Fake Description", 600, 8, 1, 2, address),
        Estate("Fake Category 8", "900 000 €", "Fake_Description", 800, 9, 2, 3, address)

    )
    val getTestList: ArrayList<Estate>
        get() = ArrayList(estateList)

    val getApartmentPhoto: ArrayList<Int>
        get() = ArrayList(photosApartment)

    val getHousePhoto: ArrayList<Int>
        get() = ArrayList(photosHouse)

    val getAddress: Address
        get() = address
}



