package com.openclassrooms.realestatemanager.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EstateDao {

    @Transaction
    @Query("SELECT * FROM estate_table")
    fun getEstateList(): Flow<List<Estate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(estate: Estate)

    @Update
    suspend fun update(estate: Estate)

    @Query("DELETE FROM estate_table WHERE id = :estateId")
    suspend fun delete(estateId: Int)

}