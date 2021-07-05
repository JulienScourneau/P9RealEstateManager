package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.repository.EstateRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class EstateViewModel(private val repository: EstateRepository) : ViewModel() {

    val allEstate: LiveData<ArrayList<Estate>> = repository.allEstate.asLiveData()

    fun insert(estate: Estate) = viewModelScope.launch {
        repository.insert(estate)
    }

    fun update(estate: Estate) = viewModelScope.launch {
        repository.update(estate)
    }

    fun delete(estate: Estate) = viewModelScope.launch {
        repository.delete(estate)
    }

    class EstateViewModelFactory(private val repository: EstateRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EstateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EstateViewModel(repository) as T
            }
            throw  IllegalArgumentException("Unknown ViewModel class")
        }
    }
}