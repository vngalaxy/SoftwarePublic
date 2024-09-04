package vn.vngalaxy.fas.presentation.views.change_name

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.FloorRepository
import vn.vngalaxy.fas.data.repository.RoomRepository
import vn.vngalaxy.fas.data.repository.UserRepository
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.model.User
import vn.vngalaxy.fas.presentation.base.BaseViewModel
import vn.vngalaxy.fas.shared.scheduler.DataResult

class ChangeNameDialogViewModel(
    private val userRepository: UserRepository,
    private val floorRepository: FloorRepository,
    private val roomRepository: RoomRepository,
) : BaseViewModel() {
    private val _changeNameResult = MutableLiveData<DataResult<Unit>>()
    val changeNameResult: LiveData<DataResult<Unit>> = _changeNameResult

    var newName: String? = null

    fun changeUserName(user: User) {
        launchTaskSync(
            onRequest = { userRepository.changeUserInfo(user) },
            onSuccess = { _changeNameResult.value = DataResult.Success(it) },
            onError = { _changeNameResult.value = DataResult.Error(it) }
        )
    }

    fun changeFloorName(floor: Floor) {
        launchTaskSync(
            onRequest = { floorRepository.changeFloorName(floor) },
            onSuccess = { _changeNameResult.value = DataResult.Success(it) },
            onError = { _changeNameResult.value = DataResult.Error(it) }
        )
    }

    fun changeApartmentName(apartment: Apartment) {
        launchTaskSync(
            onRequest = { floorRepository.changeApartmentName(apartment) },
            onSuccess = { _changeNameResult.value = DataResult.Success(it) },
            onError = { _changeNameResult.value = DataResult.Error(it) }
        )
    }

    fun changeRoomName(room: Room) {
        launchTaskSync(
            onRequest = { roomRepository.changeRoomName(room) },
            onSuccess = { _changeNameResult.value = DataResult.Success(it) },
            onError = { _changeNameResult.value = DataResult.Error(it) }
        )
    }

    fun reset() {
        _changeNameResult.value = DataResult.Empty
    }
}