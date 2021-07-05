package com.openclassrooms.realestatemanager.di

import android.app.Application
import com.openclassrooms.realestatemanager.database.EstateDatabase
import com.openclassrooms.realestatemanager.repository.EstateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class EstateApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val db by lazy { EstateDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { EstateRepository(db.EstateDao()) }
}