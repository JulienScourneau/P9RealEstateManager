package com.openclassrooms.realestatemanager.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.Address
import com.openclassrooms.realestatemanager.data.Estate
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.data.Photo
import com.openclassrooms.realestatemanager.repository.EstateRepository
import com.openclassrooms.realestatemanager.utils.ADD_ESTATE_RESULT_OK
import com.openclassrooms.realestatemanager.utils.EDIT_ESTATE_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AddEditEstateViewModel @Inject constructor(
    private val repository: EstateRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    val estateWithPhoto = state.get<EstateWithPhoto>("estate")
    private var newId: Long = 0
    private var job: Job? = null

    private fun createEstate(estate: Estate) {
        job = viewModelScope.launch {
            newId = repository.insertEstate(estate)
        }
    }

    private fun createPhoto(photo: Photo) = viewModelScope.launch {
        repository.insertPhoto(photo)
    }

    private fun updateEstate(estate: Estate) = viewModelScope.launch {
        repository.updateEstate(estate)
    }

    private fun deletePhoto(id: Long) {
        job = viewModelScope.launch {
            repository.deletePhoto(id)
        }
    }

    private val addEditEstateChannel = Channel<AddEditEstateEvent>()
    val addEditEstateEvent = addEditEstateChannel.receiveAsFlow()

    private fun navigationBack() = viewModelScope.launch {
        addEditEstateChannel.send(AddEditEstateEvent.NavigationBackWithResult(ADD_ESTATE_RESULT_OK))
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addEditEstateChannel.send(AddEditEstateEvent.ShowInvalidInputMessage(text))
    }

    private fun createEstateOnSaveClick() {
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

    private fun updateEstateOnSaveClick() {
        if (estateWithPhoto != null) {
            val updateEstate = estateWithPhoto.estate.copy(
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
        }
    }

    private suspend fun createPhotoOnSaveClick() {
        if (estatePhoto.isNotEmpty()) {
            job?.join()
            for (i in estatePhoto.indices) {
                val newPhoto = Photo(
                    estateId = newId.toInt(),
                    photoReference = estatePhoto[i].photoReference
                )
                createPhoto(newPhoto)
            }
        }
    }

    private suspend fun updatePhotoOnSaveClick() {
        if (estateWithPhoto != null) {
            try {
                job?.join()
                if (estatePhoto.isNotEmpty()) {
                    for (i in estatePhoto.indices) {
                        val newPhoto = Photo(
                            estateId = estateWithPhoto.estate.id,
                            photoReference = estatePhoto[i].photoReference
                        )
                        createPhoto(newPhoto)
                    }
                }
            } catch (e: Exception) {
                Log.d("updatePhotoException", "Exception detected")
            }
        }
    }

    private fun deletePhotoOnSaveClick() {
        if (estateWithPhoto != null && estateWithPhoto.photosList.isNotEmpty()) {
            deletePhoto(estateWithPhoto.estate.id.toLong())
        }
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
        if (estateWithPhoto != null) {
            updateEstateOnSaveClick()
            deletePhotoOnSaveClick()
            viewModelScope.launch {
                updatePhotoOnSaveClick()
            }
        } else {
            createEstateOnSaveClick()
            viewModelScope.launch {
                createPhotoOnSaveClick()
            }
        }
        navigationBack()
    }

    var estateCategory =
        state.get<String>("estateCategory") ?: estateWithPhoto?.estate?.category ?: ""
        set(value) {
            field = value
            state.set("estateCategory", value)
        }

    var estateAddressStreet =
        state.get<String>("estateAddressStreet") ?: estateWithPhoto?.estate?.address?.street ?: ""
        set(value) {
            field = value
            state.set("estateAddressStreet", value)
        }

    var estateAddressNumber =
        state.get<String>("estateAddressNumber") ?: estateWithPhoto?.estate?.address?.number ?: ""
        set(value) {
            field = value
            state.set("estateAddressNumber", value)
        }

    var estateAddressCity =
        state.get<String>("estateAddressCity") ?: estateWithPhoto?.estate?.address?.city ?: ""
        set(value) {
            field = value
            state.set("estateAddressCity", value)
        }

    var estateAddressCountry =
        state.get<String>("estateAddressCountry") ?: estateWithPhoto?.estate?.address?.country ?: ""
        set(value) {
            field = value
            state.set("estateAddressCountry", value)
        }

    var estateAddressPostalCode =
        state.get<String>("estateAddressPostalCode") ?: estateWithPhoto?.estate?.address?.postalCode
        ?: ""
        set(value) {
            field = value
            state.set("estateAddressPostalCode", value)
        }

    var estatePrice = state.get<String>("estatePrice") ?: estateWithPhoto?.estate?.price ?: ""
        set(value) {
            field = value
            state.set("estatePrice", value)
        }

    var estateArea = state.get<String>("estateArea") ?: estateWithPhoto?.estate?.area ?: ""
        set(value) {
            field = value
            state.set("estateArea", value)
        }

    var estateRoom = state.get<String>("estateRoom") ?: estateWithPhoto?.estate?.room ?: ""
        set(value) {
            field = value
            state.set("estateRoom", value)
        }

    var estateBathroom =
        state.get<String>("estateBathroom") ?: estateWithPhoto?.estate?.bathroom ?: ""
        set(value) {
            field = value
            state.set("estateBathroom", value)
        }

    var estateBedroom = state.get<String>("estateBedroom") ?: estateWithPhoto?.estate?.bedroom ?: ""
        set(value) {
            field = value
            state.set("estateBedroom", value)
        }

    var estateDescription =
        state.get<String>("estateDescription") ?: estateWithPhoto?.estate?.description ?: ""
        set(value) {
            field = value
            state.set("estateDescription", value)
        }

    var estatePhoto =
        state.get<ArrayList<Photo>>("estatePhoto") ?: estateWithPhoto?.photosList ?: emptyList()
        set(value) {
            field = value
            state.set("estatePhoto", value)
        }

    sealed class AddEditEstateEvent {
        data class ShowInvalidInputMessage(val message: String) : AddEditEstateEvent()
        data class NavigationBackWithResult(val result: Int) : AddEditEstateEvent()
    }
}