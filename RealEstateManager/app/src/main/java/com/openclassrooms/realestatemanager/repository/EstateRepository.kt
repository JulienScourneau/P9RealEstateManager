package com.openclassrooms.realestatemanager.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.data.Estate
import com.openclassrooms.realestatemanager.data.EstateDao
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.data.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class EstateRepository @Inject constructor(private val estateDao: EstateDao) {

    val allEstate: Flow<List<EstateWithPhoto>> = estateDao.getEstateList()

    fun getEstateById(id: Long): Flow<EstateWithPhoto> {
        return estateDao.getEstateById(id)
    }

    fun getSearchEstate(query: SimpleSQLiteQuery): Flow<List<EstateWithPhoto>> {
        return estateDao.getSearchEstate(query)
    }

    suspend fun insertEstate(estate: Estate): Long {
        return estateDao.insertEstate(estate)
    }

    suspend fun insertPhoto(photo: Photo) {
        estateDao.insertPhoto(photo)
    }

    suspend fun updateEstate(estate: Estate) {
        estateDao.updateEstate(estate)
    }

    suspend fun deletePhoto(id: Long) {
        estateDao.deletePhoto(id)
    }
}