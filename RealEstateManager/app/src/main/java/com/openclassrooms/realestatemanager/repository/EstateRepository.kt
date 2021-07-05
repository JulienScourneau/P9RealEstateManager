package com.openclassrooms.realestatemanager.repository

import com.openclassrooms.realestatemanager.database.EstateDao
import com.openclassrooms.realestatemanager.models.Estate
import kotlinx.coroutines.flow.Flow

class EstateRepository(private val estateDao: EstateDao) {

    val allEstate: Flow<ArrayList<Estate>> = estateDao.getEstateList()

    suspend fun insert(estate: Estate) {
        estateDao.insert(estate)
    }

    suspend fun update(estate: Estate) {
        estateDao.update(estate)
    }

    suspend fun delete(estate: Estate) {
        estateDao.delete(estate)
    }
}