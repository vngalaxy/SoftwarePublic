package vn.vngalaxy.fas.presentation.views.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.FloorRepository
import vn.vngalaxy.fas.data.repository.RoomRepository
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.presentation.base.BaseViewModel

class UserViewModel(
    private val floor: FloorRepository,
    private val roomRepository: RoomRepository,
) : BaseViewModel() {
    private val _selectedFloor = MutableLiveData<Floor>()
    val selectedFloor: LiveData<Floor> = _selectedFloor
    private val _selectedApartment = MutableLiveData<Apartment>()
    val selectedApartment: LiveData<Apartment> = _selectedApartment
    private val _selectedRoom = MutableLiveData<Room>()
    val selectedRoom: LiveData<Room> = _selectedRoom

    private val _floors = MutableLiveData<List<Floor>>()
    val floors: LiveData<List<Floor>> = _floors

    private val _roomsWithUser = MutableLiveData<List<Room>>()
    val roomsWithUser: LiveData<List<Room>> = _roomsWithUser

    fun setSelectedFloor(floor: Floor) {
        _selectedFloor.value = floor
    }

    fun setSelectedApartment(apartment: Apartment) {
        _selectedApartment.value = apartment
    }

    fun setSelectedRoom(room: Room) {
        _selectedRoom.value = room
    }

    fun loadAllFloors() {
        launchTaskSync(
            onRequest = { floor.getAllFloors() },
            onSuccess = { _floors.value = it },
            onError = { exception.value = it }
        )
    }

    fun loadRoomsWithFloor(floor: Floor) {
        launchTaskSync(
            onRequest = { roomRepository.getRoomsWithFloor(floor) },
            onSuccess = { _roomsWithUser.value = it },
            onError = { exception.value = it }
        )
    }

    fun loadRoomsWithApartment(apartment: Apartment) {
        launchTaskSync(
            onRequest = { roomRepository.getRoomsWithApartment(apartment) },
            onSuccess = { _roomsWithUser.value = it },
            onError = { exception.value = it }
        )
    }

    fun loadRoomsDetail(room: Room) {
        launchTaskSync(
            onRequest = { roomRepository.getRoomDetail(room) },
            onSuccess = { _roomsWithUser.value = listOf(it) },
            onError = { exception.value = it }
        )
    }
}