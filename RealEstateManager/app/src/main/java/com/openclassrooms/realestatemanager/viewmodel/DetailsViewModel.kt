package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.repository.EstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: EstateRepository,
    state: SavedStateHandle
) : ViewModel() {

    val estateId = state.get<Long>("id")
    private val detailsEstateEventChannel = Channel<DetailsEstateEvent>()
    val estateEvent = detailsEstateEventChannel.receiveAsFlow()

    fun getEstateById(id: Long): LiveData<EstateWithPhoto?> {
        return repository.getEstateById(id).asLiveData()
    }

    fun onEditEstateSelected(estate: EstateWithPhoto) = viewModelScope.launch {
        detailsEstateEventChannel.send(DetailsEstateEvent.NavigateToEditEstateScreen(estate))
    }

    sealed class DetailsEstateEvent{
        data class NavigateToEditEstateScreen(val estate: EstateWithPhoto) : DetailsEstateEvent()
    }
}