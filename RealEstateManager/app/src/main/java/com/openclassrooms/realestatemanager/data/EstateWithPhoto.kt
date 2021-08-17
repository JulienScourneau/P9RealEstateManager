package com.openclassrooms.realestatemanager.data

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

data class EstateWithPhoto(
    @Embedded val estate: Estate,
    @Relation(
        parentColumn = "id",
        entityColumn = "estateId"
    )
    val photosList: List<Photo> = emptyList()
) : Serializable