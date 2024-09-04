package vn.vngalaxy.fas.presentation.views.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.RoomRepository
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.presentation.base.BaseViewModel
import vn.vngalaxy.fas.shared.scheduler.DataResult

class EditRoomViewModel(
    private val roomRepository: RoomRepository,
) : BaseViewModel() {
    private val _room = MutableLiveData<Room>()
    val room: LiveData<Room> = _room

    private val _deleteRoomResult = MutableLiveData<DataResult<Unit>>()
    val deleteRoomResult: LiveData<DataResult<Unit>> = _deleteRoomResult

    fun loadRoomDetail(room: Room) {
        launchTaskSync(
            onRequest = { roomRepository.getRoomDetail(room) },
            onSuccess = { _room.value = it },
            onError = { exception.value = it }
        )
    }

    fun deleteRoom(room: Room) {
        launchTaskSync(
            onRequest = { roomRepository.deleteRoom(room) },
            onSuccess = { _deleteRoomResult.value = DataResult.Success(it) },
            onError = { _deleteRoomResult.value = DataResult.Error(it) }
        )
    }

    fun reset() {
        _deleteRoomResult.value = DataResult.Empty
    }
}