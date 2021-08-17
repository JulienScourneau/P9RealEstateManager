package com.openclassrooms.realestatemanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "estate_photo")
data class Photo(
    var photoReference: String,
    var estateId: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Serializable
