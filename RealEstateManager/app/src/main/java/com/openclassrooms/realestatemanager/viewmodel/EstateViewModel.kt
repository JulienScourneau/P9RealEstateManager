package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.Estate
import com.openclassrooms.realestatemanager.repository.EstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class EstateViewModel @Inject constructor(
    private val repository: EstateRepository
) : ViewModel() {

    val allEstate: LiveData<List<Estate>> = repository.allEstate.asLiveData()

    fun insert(estate: Estate) = viewModelScope.launch {
        repository.insert(estate)
    }

    fun update(estate: Estate) = viewModelScope.launch {
        repository.update(estate)
    }

    fun delete(estate: Estate) = viewModelScope.launch {
        repository.delete(estate)
    }

}