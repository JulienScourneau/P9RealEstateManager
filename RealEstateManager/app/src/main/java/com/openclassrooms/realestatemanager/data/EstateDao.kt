package com.openclassrooms.realestatemanager.data

import androidx.room.*
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
    suspend fun insertEstate(estate: Estate) :Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: Photo)

    @Update
    suspend fun update(estate: Estate)

    @Query("DELETE FROM estate_table WHERE id = :estateId ")
    suspend fun delete(estateId: Int)

}