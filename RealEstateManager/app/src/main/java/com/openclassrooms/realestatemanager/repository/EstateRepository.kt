package com.openclassrooms.realestatemanager.repository

import com.openclassrooms.realestatemanager.data.EstateDao
import com.openclassrooms.realestatemanager.data.Estate
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.data.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EstateRepository @Inject constructor(private val estateDao: EstateDao) {

    val allEstate: Flow<List<EstateWithPhoto>> = estateDao.getEstateList()

    fun getEstateById(id: Long): Flow<EstateWithPhoto> {
        return estateDao.getEstateById(id)
    }

    suspend fun insertEstate(estate: Estate): Long {
        return estateDao.insertEstate(estate)
    }

    suspend fun insertPhoto(photo: Photo) {
        estateDao.insertPhoto(photo)
    }

    suspend fun update(estate: Estate) {
        estateDao.update(estate)
    }

    suspend fun delete(estate: Estate) {
        estateDao.delete(estate.id)
    }
}