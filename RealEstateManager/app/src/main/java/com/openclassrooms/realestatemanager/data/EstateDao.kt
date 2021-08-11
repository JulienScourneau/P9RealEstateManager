package com.openclassrooms.realestatemanager.data

import androidx.room.*
import com.openclassrooms.realestatemanager.data.models.Estate
import com.openclassrooms.realestatemanager.data.models.EstateWithPhoto
import com.openclassrooms.realestatemanager.data.models.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface EstateDao {

    @Transaction
    @Query("SELECT * FROM estate_table")
    fun getEstateList(): Flow<List<EstateWithPhoto>>

    @Transaction
    @Query("SELECT * FROM estate_table WHERE id = :estateId")
    fun getEstateById(estateId: Long): Flow<EstateWithPhoto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEstate(estate: Estate): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: Photo)

    @Update
    suspend fun updateEstate(estate: Estate)

    @Query("DELETE FROM estate_photo WHERE estateId =:id")
    suspend fun deletePhoto(id: Long)

}