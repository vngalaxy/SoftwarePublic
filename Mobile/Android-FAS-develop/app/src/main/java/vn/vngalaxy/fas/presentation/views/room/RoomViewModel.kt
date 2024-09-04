package vn.vngalaxy.fas.presentation.views.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.FloorRepository
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.presentation.base.BaseViewModel

class RoomViewModel(
    private val floorRepository: FloorRepository,
) : BaseViewModel() {
    private val _floors = MutableLiveData<List<Floor>>()
    val floors: LiveData<List<Floor>> = _floors

    private val _apartments = MutableLiveData<List<Apartment>>()
    val apartments: LiveData<List<Apartment>> = _apartments

    fun loadAllFloors() {
        launchTaskSync(
            onRequest = { floorRepository.getAllFloors() },
            onSuccess = { _floors.value = it },
            onError = { exception.value = it }
        )
    }

    fun loadApartmentByFloor(floor: Floor) {
        _apartments.value = floors.value?.find { it.id == floor.id }?.apartments
    }

    fun loadApartmentDefault() {
        _apartments.value = floors.value?.flatMap { it.apartments }
    }
}