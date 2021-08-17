package com.openclassrooms.realestatemanager.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.*
import com.openclassrooms.realestatemanager.repository.EstateRepository
import com.openclassrooms.realestatemanager.utils.ADD_ESTATE_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
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

    private suspend fun createPhoto(photo: Photo) = viewModelScope.launch {
        repository.insertPhoto(photo)
    }

    private fun updateEstate(estate: Estate) = viewModelScope.launch {
        repository.updateEstate(estate)
    }

    private suspend fun deletePhoto(id: Long) {
        repository.deletePhoto(id)
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
            isSold = estateIsSold,
            pointOfInterest = PointOfInterest(
                school = estateSchool,
                localCommerce = estateLocalCommerce,
                publicTransport = estatePublicTransport,
                park = estatePark
            ),
            contact = RealEstateAgent(
                name = estateContactName,
                phoneNumber = estateContactPhoneNumber
            ),
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
                isSold = estateIsSold,
                pointOfInterest = PointOfInterest(
                    school = estateSchool,
                    localCommerce = estateLocalCommerce,
                    publicTransport = estatePublicTransport,
                    park = estatePark
                ),
                contact = RealEstateAgent(
                    name = estateContactName,
                    phoneNumber = estateContactPhoneNumber
                ),
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
                e.stackTrace
                Log.d("updatePhotoException", "$e")
            }
        }
    }

    private suspend fun deletePhotoOnSaveClick() {
        if (estateWithPhoto != null) {
            deletePhoto(estateWithPhoto.estate.id.toLong())
        }
    }

    fun onSaveClick() {
        when {
            estateCategory.isBlank() -> {
                showInvalidInputMessage("Select a category")
                return
            }
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
        }
        if (estateWithPhoto != null) {
            updateEstateOnSaveClick()
            viewModelScope.launch {
                deletePhotoOnSaveClick()
            }
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

    var estateIsSold =
        state.get<Boolean>("estateIsSold") ?: estateWithPhoto?.estate?.isSold ?: false
        set(value) {
            field = value
            state.set("estateIdSold", value)
        }

    var estateSchool =
        state.get<Boolean>("estateSchool") ?: estateWithPhoto?.estate?.pointOfInterest?.school
        ?: false
        set(value) {
            field = value
            state.set("estateSchool", value)
        }
    var estateLocalCommerce =
        state.get<Boolean>("estateLocalCommerce")
            ?: estateWithPhoto?.estate?.pointOfInterest?.localCommerce ?: false
        set(value) {
            field = value
            state.set("estateLocalCommerce", value)
        }
    var estatePublicTransport =
        state.get<Boolean>("estatePublicTransport")
            ?: estateWithPhoto?.estate?.pointOfInterest?.publicTransport ?: false
        set(value) {
            field = value
            state.set("estatePublicTransport", value)
        }
    var estatePark =
        state.get<Boolean>("estatePark") ?: estateWithPhoto?.estate?.pointOfInterest?.park ?: false
        set(value) {
            field = value
            state.set("estatePark", value)
        }

    var estatePhoto =
        state.get<ArrayList<Photo>>("estatePhoto") ?: estateWithPhoto?.photosList
        ?: ArrayList<Photo>()
        set(value) {
            field = value
            state.set("estatePhoto", value)
        }

    var estateContactName =
        state.get<String>("estateContactName") ?: estateWithPhoto?.estate?.contact?.name ?: ""
        set(value) {
            field = value
            state.set("estateContact", value)
        }

    var estateContactPhoneNumber = state.get<String>("estateContactPhoneNumber")
        ?: estateWithPhoto?.estate?.contact?.phoneNumber ?: ""
        set(value) {
            field = value
            state.set("estateContactPhoneNumber", value)
        }

    sealed class AddEditEstateEvent {
        data class ShowInvalidInputMessage(val message: String) : AddEditEstateEvent()
        data class NavigationBackWithResult(val result: Int) : AddEditEstateEvent()
    }
}