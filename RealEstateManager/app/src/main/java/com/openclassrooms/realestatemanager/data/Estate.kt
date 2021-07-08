package com.openclassrooms.realestatemanager.data


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "estate_table")

data class Estate(

    //@Embedded var photos: Photo,
    var category: String,
    var price: String,
    var description: String,
    var area: Int,
    var room: Int,
    var bathrooms: Int,
    var bedrooms: Int,
    @Embedded var address: Address,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

data class Photo(
    var photo: List<Int>
)

data class Address(
    var number: Int,
    var street: String,
    var city: String,
    var country: String,
    @ColumnInfo var postalCode: Int
)
