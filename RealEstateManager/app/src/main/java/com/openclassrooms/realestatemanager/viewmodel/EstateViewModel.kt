package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.data.Search
import com.openclassrooms.realestatemanager.repository.EstateRepository
import com.openclassrooms.realestatemanager.utils.ADD_ESTATE_RESULT_OK
import com.openclassrooms.realestatemanager.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstateViewModel @Inject constructor(
    private val repository: EstateRepository,
    state: SavedStateHandle
) : ViewModel() {

    var searchEstate = state.get<Search>("search")
    private val estateEventChannel = Channel<EstateEvent>()
    val estateEvent = estateEventChannel.receiveAsFlow()
    val allEstate: LiveData<List<EstateWithPhoto>> = repository.allEstate.asLiveData()

    fun getEstateBySearch(query: SimpleSQLiteQuery): LiveData<List<EstateWithPhoto>> {
        return repository.getSearchEstate(query).asLiveData()
    }

    fun onEstateSelected(id: Long) = viewModelScope.launch {
        estateEventChannel.send(EstateEvent.NavigateToDetailsScreen(id))
    }

    fun onAddNewEstateClick() = viewModelScope.launch {
        estateEventChannel.send(EstateEvent.NavigateToAddEstateScreen)
    }

    fun onSearchEstateClick()= viewModelScope.launch {
        estateEventChannel.send(EstateEvent.NavigateToSearchScreen)
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
        object NavigateToSearchScreen : EstateEvent()
        data class NavigateToDetailsScreen(val id: Long) : EstateEvent()
        data class ShowEstateAddedConfirmationMessage(val msg: String) : EstateEvent()
    }

}