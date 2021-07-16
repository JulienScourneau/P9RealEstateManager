package com.openclassrooms.realestatemanager.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class EstateWithPhoto(
    @Embedded val estate: Estate,
    @Relation(

        parentColumn = "id",
        entityColumn = "estateId"
    )
    val photos: List<Photo> = emptyList()
)


@Entity(tableName = "estate_table")
data class Estate(

    var category: String,
    var price: String,
    var description: String,
    var area: String,
    var room: String,
    var bathroom: String,
    var bedroom: String,
    @Embedded var address: Address,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

@Entity(tableName = "estate_photo")
data class Photo(
    @PrimaryKey var photo: String,
    var estateId: Int
)

data class Address(
    var number: String,
    var street: String,
    var city: String,
    var country: String,
    var postalCode: String
)
