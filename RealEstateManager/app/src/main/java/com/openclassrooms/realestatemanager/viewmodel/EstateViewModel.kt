package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.Estate
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.repository.EstateRepository
import com.openclassrooms.realestatemanager.utils.ADD_ESTATE_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstateViewModel @Inject constructor(
    private val repository: EstateRepository
) : ViewModel() {

    private val estateEventChannel = Channel<EstateEvent>()
    val estateEvent = estateEventChannel.receiveAsFlow()
    val allEstate: LiveData<List<EstateWithPhoto>> = repository.allEstate.asLiveData()

    fun insert(estate: Estate) = viewModelScope.launch {
        repository.insertEstate(estate)
    }

    fun update(estate: Estate) = viewModelScope.launch {
        repository.update(estate)
    }

    fun delete(estate: Estate) = viewModelScope.launch {
        repository.delete(estate)
    }

    fun onEstateSelected(estate: EstateWithPhoto) = viewModelScope.launch {
        estateEventChannel.send(EstateEvent.NavigateToDetailsScreen(estate))
    }

    fun onAddNewEstateClick() = viewModelScope.launch {
        estateEventChannel.send(EstateEvent.NavigateToAddEstateScreen)
    }

    fun onAddResult(result: Int, text: String) {
        when (result) {
            ADD_ESTATE_RESULT_OK -> showEstateAddedConfirmationMessage(text)
        }
    }

    private fun showEstateAddedConfirmationMessage(text: String) = viewModelScope.launch {
        estateEventChannel.send(EstateEvent.ShowEstateAddedConfirmationMessage(text))
    }

    sealed class EstateEvent {
        object NavigateToAddEstateScreen : EstateEvent()
        data class NavigateToDetailsScreen(val estate: EstateWithPhoto) : EstateEvent()
        data class ShowEstateAddedConfirmationMessage(val msg: String) : EstateEvent()
    }

}