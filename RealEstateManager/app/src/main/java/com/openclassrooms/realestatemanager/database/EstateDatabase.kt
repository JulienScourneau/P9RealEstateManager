package com.openclassrooms.realestatemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.models.Estate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Estate::class], version = 1)
abstract class EstateDatabase : RoomDatabase() {

    abstract fun EstateDao(): EstateDao

    companion object {
        private var INSTANCE: EstateDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): EstateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EstateDatabase::class.java,
                    "estate_database"
                )
                    .addCallback(EstateDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class EstateDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { estateDatabase ->
                scope.launch {
                    var estateDao = estateDatabase.EstateDao()

                }
            }
        }
    }
}