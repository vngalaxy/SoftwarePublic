package vn.vngalaxy.fas.presentation.views.add_common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.appwrite.ID
import vn.vngalaxy.fas.data.repository.FloorRepository
import vn.vngalaxy.fas.data.repository.RoomRepository
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.presentation.base.BaseViewModel
import vn.vngalaxy.fas.shared.scheduler.DataResult

class AddCommonViewModel(
    private val floorRepository: FloorRepository,
    private val roomRepository: RoomRepository,
) : BaseViewModel() {
    private val _addResult = MutableLiveData<DataResult<Unit>>()
    val addResult: LiveData<DataResult<Unit>> = _addResult

    var newName: String? = null

    fun addFloor() {
        val newFloor = Floor(ID.unique(), newName)

        launchTaskSync(
            onRequest = { floorRepository.addFloor(newFloor) },
            onSuccess = { _addResult.value = DataResult.Success(it) },
            onError = { _addResult.value = DataResult.Error(it) }
        )
    }

    fun addApartment(floor: Floor?) {
        val newApartment = Apartment(ID.unique(), newName, floor = floor)

        launchTaskSync(
            onRequest = { floorRepository.addApartment(newApartment) },
            onSuccess = { _addResult.value = DataResult.Success(it) },
            onError = { _addResult.value = DataResult.Error(it) }
        )
    }

    fun addRoom(apartment: Apartment?) {
        val newRoom = Room(id = ID.unique(), name = newName, apartment = apartment)

        launchTaskSync(
            onRequest = { roomRepository.addRoom(newRoom) },
            onSuccess = { _addResult.value = DataResult.Success(it) },
            onError = { _addResult.value = DataResult.Error(it) }
        )
    }

    fun reset() {
        _addResult.value = DataResult.Empty
    }
}