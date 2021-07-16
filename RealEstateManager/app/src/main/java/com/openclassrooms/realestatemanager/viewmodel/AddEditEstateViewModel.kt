package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.Address
import com.openclassrooms.realestatemanager.data.Estate
import com.openclassrooms.realestatemanager.repository.EstateRepository
import com.openclassrooms.realestatemanager.utils.ADD_ESTATE_RESULT_OK
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditEstateViewModel @Inject constructor(
    private val repository: EstateRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    val estate = state.get<Estate>("estate")

    private fun createEstate(estate: Estate) = viewModelScope.launch {
        repository.insertEstate(estate)
        addEditEstateChannel.send(AddEditEstateEvent.NavigationBackWithResult(ADD_ESTATE_RESULT_OK))
    }

    private val addEditEstateChannel = Channel<AddEditEstateEvent>()
    val addEditEstateEvent = addEditEstateChannel.receiveAsFlow()

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addEditEstateChannel.send(AddEditEstateEvent.ShowInvalidInputMessage(text))
    }

    fun onSaveClick() {
        if (estateAddressStreet.isBlank()) {
            showInvalidInputMessage(R.string.add_edit_estate_on_save_click_street.toString())
            return
        }

        if (estate != null) {
            val updateEstate = estate.copy(
                category = estateCategory,
                price = estatePrice,
                description = estateDescription,
                area = estateArea,
                room = estateRoom,
                bathroom = estateBathroom,
                bedroom = estateBedroom,
                address = Address(
                    number = estateAddressNumber,
                    street = estateAddressStreet,
                    city = estateAddressCity,
                    country = estateAddressCountry,
                    postalCode = estateAddressPostalCode
                )
            )
        } else {
            val newEstate = Estate(
                category = estateCategory,
                price = estatePrice,
                description = estateDescription,
                area = estateArea,
                room = estateRoom,
                bathroom = estateBathroom,
                bedroom = estateBedroom,
                address = Address(
                    number = estateAddressNumber,
                    street = estateAddressStreet,
                    city = estateAddressCity,
                    country = estateAddressCountry,
                    postalCode = estateAddressPostalCode
                )
            )
            createEstate(newEstate)
        }
    }

    sealed class AddEditEstateEvent {
        data class ShowInvalidInputMessage(val message: String) : AddEditEstateEvent()
        data class NavigationBackWithResult(val result: Int) : AddEditEstateEvent()
    }


    var estateCategory = state.get<String>("estateCategory") ?: estate?.category ?: ""
        set(value) {
            field = value
            state.set("estateCategory", value)
        }

    var estateAddressStreet =
        state.get<String>("estateAddressStreet") ?: estate?.address?.street ?: ""
        set(value) {
            field = value
            state.set("estateAddressStreet", value)
        }

    var estateAddressNumber =
        state.get<String>("estateAddressNumber") ?: estate?.address?.number ?: ""
        set(value) {
            field = value
            state.set("estateAddressNumber", value)
        }

    var estateAddressCity = state.get<String>("estateAddressCity") ?: estate?.address?.city ?: ""
        set(value) {
            field = value
            state.set("estateAddressCity", value)
        }

    var estateAddressCountry =
        state.get<String>("estateAddressCountry") ?: estate?.address?.country ?: ""
        set(value) {
            field = value
            state.set("estateAddressCountry", value)
        }

    var estateAddressPostalCode =
        state.get<String>("estateAddressPostalCode") ?: estate?.address?.postalCode ?: ""
        set(value) {
            field = value
            state.set("estateAddressPostalCode", value)
        }

    var estatePrice = state.get<String>("estatePrice") ?: estate?.price ?: ""
        set(value) {
            field = value
            state.set("estatePrice", value)
        }

    var estateArea = state.get<String>("estateArea") ?: estate?.area ?: ""
        set(value) {
            field = value
            state.set("estateArea", value)
        }

    var estateRoom = state.get<String>("estateRoom") ?: estate?.room ?: ""
        set(value) {
            field = value
            state.set("estateRoom", value)
        }

    var estateBathroom = state.get<String>("estateBathroom") ?: estate?.bathroom ?: ""
        set(value) {
            field = value
            state.set("estateBathroom", value)
        }

    var estateBedroom = state.get<String>("estateBedroom") ?: estate?.bedroom ?: ""
        set(value) {
            field = value
            state.set("estateBedroom", value)
        }

    var estateDescription = state.get<String>("estateDescription") ?: estate?.description ?: ""
        set(value) {
            field = value
            state.set("estateDescription", value)
        }
}

