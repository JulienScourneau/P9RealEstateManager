package com.openclassrooms.realestatemanager.viewmodel

import android.util.Log
import androidx.datastore.preferences.protobuf.Empty
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.*
import com.openclassrooms.realestatemanager.repository.EstateRepository
import com.openclassrooms.realestatemanager.utils.ADD_ESTATE_RESULT_OK
import com.openclassrooms.realestatemanager.utils.EDIT_ESTATE_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditEstateViewModel @Inject constructor(
    private val repository: EstateRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    val estateWithPhoto = state.get<EstateWithPhoto>("estate")
    val title = state.get<String>("title")
    private var newId: Long = 0
    private var job: Job? = null

    private fun createEstate(estate: Estate) {
        runBlocking {
            newId = repository.insertEstate(estate)
        }
    }

    private fun createPhoto(photo: Photo) {
        runBlocking {
            repository.insertPhoto(photo)
            Log.d("createPhoto", "estateId: ${photo.estateId}")
        }
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
        if (estateWithPhoto == null)
            addEditEstateChannel.send(
                AddEditEstateEvent.NavigationBackWithResult(
                    ADD_ESTATE_RESULT_OK
                )
            ) else addEditEstateChannel.send(
            AddEditEstateEvent.NavigationBackWithResult(
                EDIT_ESTATE_RESULT_OK
            )
        )
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
            date = estateDate,
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
                date = estateDate,
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

    private fun createPhotoOnSaveClick() {
        try {
            if (estatePhoto.isNotEmpty()) {
                for (i in estatePhoto.indices) {
                    val newPhoto = Photo(
                        estateId = newId.toInt(),
                        photoReference = estatePhoto[i].photoReference
                    )
                    Log.d("createPhotoOnSaveClick", "estateId: $newId")
                    viewModelScope.launch { createPhoto(newPhoto) }
                }
            }
        } catch (e: Exception) {
            e.stackTrace
            Log.d("createPhotoException", "$e")
        }
        Log.d("createPhotoOnSaveClick", "finish")
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
            Log.d("OnSaveClick", "Add photo begin")
            viewModelScope.launch(Dispatchers.Main) {

            }
            viewModelScope.launch {
                createPhotoOnSaveClick()
            }
            Log.d("OnSaveClick", "Add photo end")
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

    var estateDate =
        state.get<Long>("estateDate") ?: estateWithPhoto?.estate?.date ?: System.currentTimeMillis()
        set(value) {
            field = value
            state.set("estateDate", value)
        }

    sealed class AddEditEstateEvent {
        data class ShowInvalidInputMessage(val message: String) : AddEditEstateEvent()
        data class NavigationBackWithResult(val result: Int) : AddEditEstateEvent()
    }
}