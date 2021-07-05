package com.openclassrooms.realestatemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.models.Estate

@Database(entities = [Estate::class], version = 1)
abstract class EstateDatabase : RoomDatabase() {

    abstract fun EstateDao(): EstateDao

    companion object {
        private var INSTANCE: EstateDatabase? = null

        fun getDatabase(context: Context): EstateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EstateDatabase::class.java,
                    "estate_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}