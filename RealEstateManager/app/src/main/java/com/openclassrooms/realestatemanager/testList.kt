package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.models.Address
import com.openclassrooms.realestatemanager.models.Estate
import kotlin.collections.ArrayList


object TestList {
    private val photos = mutableListOf<Int>(
        R.drawable.apartment,
        R.drawable.living_room,
        R.drawable.kitchen,
        R.drawable.bathroom,
        R.drawable.bedroom
    )

    private val address =
        Address(100, "Fake Street", "Additional Address", "Fake Country", "000000")

    private val estateList = mutableListOf<Estate>(
        Estate(photos, "Fake_category 1", "700 000 €", "Fake Description", 600, 8, 1, 2, address),
        Estate(photos, "Fake Category 2", "800 000 €", "Fake_Description", 800, 9, 2, 3, address)
    )
    val getTestList: ArrayList<Estate>
        get() = ArrayList(estateList)
}



