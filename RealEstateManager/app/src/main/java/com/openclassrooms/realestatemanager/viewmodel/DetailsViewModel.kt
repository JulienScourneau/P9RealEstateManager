package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.repository.EstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: EstateRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    val estateId = state.get<Long>("id")

    fun getEstateById(id: Long): LiveData<EstateWithPhoto?> {
        return repository.getEstateById(id).asLiveData()
    }


}