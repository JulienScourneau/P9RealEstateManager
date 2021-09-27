package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.repository.EstateRepository
import com.openclassrooms.realestatemanager.utils.EDIT_ESTATE_RESULT_OK
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
    val estateWithPhoto: LiveData<EstateWithPhoto> =
        estateId?.let { repository.getEstateById(it).asLiveData() }!!

    fun onEditEstateSelected(estate: EstateWithPhoto) = viewModelScope.launch {
        detailsEstateEventChannel.send(DetailsEstateEvent.NavigateToEditEstateScreen(estate))
    }

    fun onEditResult(result: Int, text: String) {
        when (result) {
            EDIT_ESTATE_RESULT_OK -> showEstateUpdatedConfirmationMessage(text)
        }
    }

    private fun showEstateUpdatedConfirmationMessage(text: String) = viewModelScope.launch {
        detailsEstateEventChannel.send(DetailsEstateEvent.ShowEstateUpdatedConfirmationMessage(text))
    }

    sealed class DetailsEstateEvent {
        data class NavigateToEditEstateScreen(val estate: EstateWithPhoto) : DetailsEstateEvent()
        data class ShowEstateUpdatedConfirmationMessage(val msg: String) : DetailsEstateEvent()
    }
}