package vn.vngalaxy.fas.presentation.views.change_room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.SensorRepository
import vn.vngalaxy.fas.data.repository.UserRepository
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.model.Sensor
import vn.vngalaxy.fas.model.User
import vn.vngalaxy.fas.presentation.base.BaseViewModel
import vn.vngalaxy.fas.shared.scheduler.DataResult

class ChangeRoomDialogViewModel(
    private val sensorRepository: SensorRepository,
    private val userRepository: UserRepository,
) : BaseViewModel() {
    private val _selectedFloor = MutableLiveData<Floor>()
    val selectedFloor: LiveData<Floor> = _selectedFloor
    private val _selectedApartment = MutableLiveData<Apartment>()
    val selectedApartment: LiveData<Apartment> = _selectedApartment
    private val _selectedRoom = MutableLiveData<Room>()
    val selectedRoom: LiveData<Room> = _selectedRoom

    private val _changeRoomResult = MutableLiveData<DataResult<Unit>>()
    val changeRoomResult: LiveData<DataResult<Unit>> = _changeRoomResult

    fun setSelectedFloor(floor: Floor) {
        _selectedFloor.value = floor
    }

    fun setSelectedApartment(apartment: Apartment) {
        _selectedApartment.value = apartment
    }

    fun setSelectedRoom(room: Room) {
        _selectedRoom.value = room
    }

    fun changeSensorRoom(sensor: Sensor) {
        launchTaskSync(
            onRequest = { sensorRepository.changeRoomOfSensor(sensor) },
            onSuccess = { _changeRoomResult.value = DataResult.Success(it) },
            onError = { _changeRoomResult.value = DataResult.Error(it) }
        )
    }

    fun changeUserRoom(user: User) {
        launchTaskSync(
            onRequest = { userRepository.changeRoomOfUser(user) },
            onSuccess = { _changeRoomResult.value = DataResult.Success(it) },
            onError = { _changeRoomResult.value = DataResult.Error(it) }
        )
    }

    fun reset() {
        _changeRoomResult.value = DataResult.Empty
    }
}