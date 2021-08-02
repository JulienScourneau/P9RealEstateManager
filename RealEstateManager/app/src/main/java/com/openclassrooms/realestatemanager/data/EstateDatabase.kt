package com.openclassrooms.realestatemanager.data

import android.content.ContentResolver
import android.net.Uri
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.ApplicationScope
import com.openclassrooms.realestatemanager.utils.TestList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [Estate::class, Photo::class], version = 1, exportSchema = false)
abstract class EstateDatabase : RoomDatabase() {

    abstract fun estateDao(): EstateDao

    class CallBack @Inject constructor(
        private val database: Provider<EstateDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().estateDao()

            applicationScope.launch {
                dao.insertEstate(TestList.getEstate)
                dao.insertEstate(TestList.getEstate1)
                dao.insertEstate(TestList.getEstate2)

                dao.insertPhoto(Photo("android.resource://com.openclassrooms.realestatemanager/drawable/house", 1))
                dao.insertPhoto(Photo("android.resource://com.openclassrooms.realestatemanager/drawable/apartment", 2))
                dao.insertPhoto(Photo("android.resource://com.openclassrooms.realestatemanager/drawable/house", 3))
                dao.insertPhoto(Photo("android.resource://com.openclassrooms.realestatemanager/drawable/living_room", 1))
                dao.insertPhoto(Photo("android.resource://com.openclassrooms.realestatemanager/drawable/bedroom", 2))
                dao.insertPhoto(Photo("android.resource://com.openclassrooms.realestatemanager/drawable/kitchen", 3))

            }
        }
    }
}