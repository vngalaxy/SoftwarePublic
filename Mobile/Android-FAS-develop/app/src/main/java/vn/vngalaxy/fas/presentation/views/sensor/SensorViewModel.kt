package vn.vngalaxy.fas.presentation.views.sensor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.FloorRepository
import vn.vngalaxy.fas.data.repository.RoomRepository
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.presentation.base.BaseViewModel

class SensorViewModel(
    private val floor: FloorRepository,
    private val roomRepository: RoomRepository,
) : BaseViewModel() {
    private val _floors = MutableLiveData<List<Floor>>()
    val floors: LiveData<List<Floor>> = _floors

    private val _roomsWithSensor = MutableLiveData<List<Room>>()
    val roomsWithSensor: LiveData<List<Room>> = _roomsWithSensor

    private val _selectedFloor = MutableLiveData<Floor>()
    val selectedFloor: LiveData<Floor> = _selectedFloor
    private val _selectedApartment = MutableLiveData<Apartment>()
    val selectedApartment: LiveData<Apartment> = _selectedApartment
    private val _selectedRoom = MutableLiveData<Room>()
    val selectedRoom: LiveData<Room> = _selectedRoom

    fun setSelectedFloor(floor: Floor) {
        _selectedFloor.value = floor
    }

    fun setSelectedApartment(apartment: Apartment) {
        _selectedApartment.value = apartment
    }

    fun setSelectedRoom(room: Room) {
        _selectedRoom.value = room
    }

    init {
        loadAllFloors()
    }

    private fun loadAllFloors() {
        launchTaskSync(
            onRequest = { floor.getAllFloors() },
            onSuccess = { _floors.value = it },
            onError = { exception.value = it }
        )
    }

    fun loadRoomsWithFloor() {
        if (selectedFloor.value != null) {
            launchTaskSync(
                onRequest = { roomRepository.getRoomsWithFloor(selectedFloor.value!!) },
                onSuccess = { _roomsWithSensor.value = it },
                onError = { exception.value = it }
            )
        }
    }

    fun loadRoomsWithApartment() {
        if (selectedApartment.value != null) {
            launchTaskSync(
                onRequest = { roomRepository.getRoomsWithApartment(selectedApartment.value!!) },
                onSuccess = { _roomsWithSensor.value = it },
                onError = { exception.value = it }
            )
        }
    }

    fun loadRoomsDetail() {
        if (selectedRoom.value != null) {
            launchTaskSync(
                onRequest = { roomRepository.getRoomDetail(selectedRoom.value!!) },
                onSuccess = { _roomsWithSensor.value = listOf(it) },
                onError = { exception.value = it }
            )
        }
    }
}