package com.openclassrooms.realestatemanager.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.DateFormat

@Entity(tableName = "estate_table")
data class Estate(
    var category: String,
    var price: String,
    var description: String,
    var area: String,
    var room: String,
    var bathroom: String,
    var bedroom: String,
    var isSold: Boolean,
    var date: Long = System.currentTimeMillis(),
    @Embedded var contact: RealEstateAgent,
    @Embedded var pointOfInterest: PointOfInterest,
    @Embedded var address: Address,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Serializable {
    val createdDateFormatted: String
    get() = DateFormat.getDateTimeInstance().format(date)
}