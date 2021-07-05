package com.openclassrooms.realestatemanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estate_table")
data class Estate(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
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
