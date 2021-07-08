package com.openclassrooms.realestatemanager.repository

import com.openclassrooms.realestatemanager.data.EstateDao
import com.openclassrooms.realestatemanager.data.Estate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EstateRepository @Inject constructor(private val estateDao: EstateDao) {

    val allEstate: Flow<List<Estate>> = estateDao.getEstateList()

    suspend fun insert(estate: Estate) {
        estateDao.insert(estate)
    }

    suspend fun update(estate: Estate) {
        estateDao.update(estate)
    }

    suspend fun delete(estate: Estate) {
        estateDao.delete(estate.id)
    }
}