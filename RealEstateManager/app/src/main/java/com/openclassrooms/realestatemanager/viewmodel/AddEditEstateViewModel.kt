package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.Address
import com.openclassrooms.realestatemanager.data.Estate
import com.openclassrooms.realestatemanager.repository.EstateRepository
import com.openclassrooms.realestatemanager.utils.ADD_ESTATE_RESULT_OK
import com.openclassrooms.realestatemanager.utils.EDIT_ESTATE_RESULT_OK
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

    private fun updateEstate(estate: Estate) = viewModelScope.launch {
        repository.update(estate)
        addEditEstateChannel.send(AddEditEstateEvent.NavigationBackWithResult(EDIT_ESTATE_RESULT_OK))
    }

    private val addEditEstateChannel = Channel<AddEditEstateEvent>()
    val addEditEstateEvent = addEditEstateChannel.receiveAsFlow()

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addEditEstateChannel.send(AddEditEstateEvent.ShowInvalidInputMessage(text))
    }

    fun onSaveClick() {
        when {
            estateAddressStreet.isBlank() -> {
                showInvalidInputMessage("Street cannot be empty")
                return
            }
            estateAddressNumber.isBlank() -> {
                showInvalidInputMessage("Number cannot be empty")
                return
            }
            estateAddressCity.isBlank() -> {
                showInvalidInputMessage("City cannot be empty")
                return
            }
            estateAddressCountry.isBlank() -> {
                showInvalidInputMessage("Country cannot be empty")
                return
            }
            estateAddressPostalCode.isBlank() -> {
                showInvalidInputMessage("Postal Code cannot be empty")
                return
            }
            estatePrice.isBlank() -> {
                showInvalidInputMessage("Price cannot be empty")
                return
            }
            estateArea.isBlank() -> {
                showInvalidInputMessage("Area cannot be empty")
                return
            }
            estateRoom.isBlank() -> {
                showInvalidInputMessage("Number of room cannot be empty")
                return
            }
            estateBathroom.isBlank() -> {
                showInvalidInputMessage("Number of bathroom cannot be empty")
                return
            }
            estateBedroom.isBlank() -> {
                showInvalidInputMessage("Number of bedroom cannot be empty")
                return
            }
            estateDescription.isBlank() -> {
                showInvalidInputMessage("Description cannot be empty")
                return
            }
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
            updateEstate(updateEstate)
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

