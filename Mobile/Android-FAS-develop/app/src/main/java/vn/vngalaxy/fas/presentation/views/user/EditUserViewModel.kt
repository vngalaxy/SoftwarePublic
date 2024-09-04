package vn.vngalaxy.fas.presentation.views.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.FloorRepository
import vn.vngalaxy.fas.data.repository.RoomRepository
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.presentation.base.BaseViewModel

class EditUserViewModel(
    private val floorRepository: FloorRepository,
    private val roomRepository: RoomRepository,
) : BaseViewModel() {
    private val _floors = MutableLiveData<List<Floor>>()
    val floors: LiveData<List<Floor>> = _floors

    private val _room = MutableLiveData<Room>()
    val room: LiveData<Room> = _room

    init {
        loadAllFloors()
    }

    private fun loadAllFloors() {
        launchTaskSync(
            onRequest = { floorRepository.getAllFloors() },
            onSuccess = { _floors.value = it },
            onError = { exception.value = it }
        )
    }

    fun loadRoomsDetail(room: Room) {
        launchTaskSync(
            onRequest = { roomRepository.getRoomDetail(room) },
            onSuccess = { _room.value = it },
            onError = { exception.value = it }
        )
    }
}