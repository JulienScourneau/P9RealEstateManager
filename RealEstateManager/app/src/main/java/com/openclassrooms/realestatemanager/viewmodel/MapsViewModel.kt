package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.repository.EstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val repository: EstateRepository
) : ViewModel() {

    val allEstate: LiveData<List<EstateWithPhoto>> = repository.allEstate.asLiveData()

}