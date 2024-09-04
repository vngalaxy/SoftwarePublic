package vn.vngalaxy.fas.presentation.views.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.appwrite.ID
import vn.vngalaxy.fas.data.repository.UserRepository
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.model.User
import vn.vngalaxy.fas.presentation.base.BaseViewModel
import vn.vngalaxy.fas.shared.constant.Constant
import vn.vngalaxy.fas.shared.scheduler.DataResult

class AddUserViewModel(
    private val userRepository: UserRepository,
) : BaseViewModel() {
    private val _selectedFloor = MutableLiveData<Floor>()
    val selectedFloor: LiveData<Floor> = _selectedFloor
    private val _selectedApartment = MutableLiveData<Apartment>()
    val selectedApartment: LiveData<Apartment> = _selectedApartment
    private val _selectedRoom = MutableLiveData<Room>()
    val selectedRoom: LiveData<Room> = _selectedRoom

    var userName = ""
    var phoneNumber = ""

    private val _addUserResult = MutableLiveData<DataResult<Unit>>()
    val addUserResult: LiveData<DataResult<Unit>> = _addUserResult

    fun setSelectedFloor(floor: Floor) {
        _selectedFloor.value = floor
    }

    fun setSelectedApartment(apartment: Apartment) {
        _selectedApartment.value = apartment
    }

    fun setSelectedRoom(room: Room) {
        _selectedRoom.value = room
    }

    fun createUser() {
        val phoneWithCode = Constant.PRIMARY_COUNTRY_CODE.plus(phoneNumber)
        val newUser = User(
            id = ID.unique(),
            name = userName,
            phoneNumber = phoneWithCode,
            room = selectedRoom.value
        )

        launchTaskSync(
            onRequest = { userRepository.addUser(newUser) },
            onSuccess = { _addUserResult.value = DataResult.Success(Unit) },
            onError = { _addUserResult.value = DataResult.Error(it) }
        )
    }

    fun reset() {
        _addUserResult.value = DataResult.Empty
    }
}