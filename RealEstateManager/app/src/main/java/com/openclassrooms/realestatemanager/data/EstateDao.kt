package com.openclassrooms.realestatemanager.data

import android.database.Cursor
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow


@Dao
interface EstateDao {

    @Transaction
    @Query("SELECT DISTINCT estate_table.* FROM estate_table")
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

    @Transaction
    @RawQuery
    fun getSearchEstate(query: SupportSQLiteQuery?): Flow<List<EstateWithPhoto>>
    
    @Query("SELECT DISTINCT estate_table.* FROM estate_table WHERE id = :estateId")
    fun getEstateByIdCursor(estateId: Long): Cursor?

    @Query("SELECT DISTINCT estate_table.* FROM estate_table ")
    fun getAllEstateCursor(): Cursor

}