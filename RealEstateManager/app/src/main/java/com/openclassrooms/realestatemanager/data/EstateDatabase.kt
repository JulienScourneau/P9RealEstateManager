package com.openclassrooms.realestatemanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.di.ApplicationScope
import com.openclassrooms.realestatemanager.utils.TestList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Estate::class], version = 1, exportSchema = false)
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
                dao.insert(Estate("Fake_category 1", "700 000 €", "Fake Description",
                    600, 8, 1, 2, TestList.getAddress))

                dao.insert(Estate("Fake Category 2", "800 000 €", "Fake_Description",
                    800, 9, 2, 3, TestList.getAddress1))

                dao.insert(Estate("Fake_category 3", "707 000 €", "Fake Description",
                    600, 8, 1, 2, TestList.getAddress2))

                dao.insert(Estate("Fake Category 4", "990 000 €", "Fake_Description",
                    800, 9, 2, 3, TestList.getAddress))

                dao.insert(Estate("Fake_category 5", "550 000 €", "Fake Description",
                    600, 8, 1, 2, TestList.getAddress1))

                dao.insert(Estate("Fake Category 6", "650 000 €", "Fake_Description",
                    800, 9, 2, 3, TestList.getAddress2))

                dao.insert(Estate("Fake_category 7", "500 000 €", "Fake Description",
                    600, 8, 1, 2, TestList.getAddress))

                dao.insert(Estate("Fake Category 8", "900 000 €", "Fake_Description",
                    800, 9, 2, 3, TestList.getAddress1))
            }
        }
    }
}