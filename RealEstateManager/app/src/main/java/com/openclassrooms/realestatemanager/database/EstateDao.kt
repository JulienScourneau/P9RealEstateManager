package com.openclassrooms.realestatemanager.database

import androidx.room.*
import com.openclassrooms.realestatemanager.models.Estate
import kotlinx.coroutines.flow.Flow

@Dao
interface EstateDao {

    @Query("SELECT * FROM estate_table")
    fun getEstateList(): Flow<ArrayList<Estate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(estate: Estate)

    @Update
    suspend fun update(estate: Estate)

    @Delete
    suspend fun delete(estate: Estate)

}