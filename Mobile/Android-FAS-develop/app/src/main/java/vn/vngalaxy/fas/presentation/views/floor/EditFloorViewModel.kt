package vn.vngalaxy.fas.presentation.views.floor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.FloorRepository
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.presentation.base.BaseViewModel
import vn.vngalaxy.fas.shared.scheduler.DataResult

class EditFloorViewModel(
    private val floorRepository: FloorRepository,
) : BaseViewModel() {
    private val _floor = MutableLiveData<Floor>()
    val floor: LiveData<Floor> = _floor

    private val _deleteFloorResult = MutableLiveData<DataResult<Unit>>()
    val deleteFloorResult: LiveData<DataResult<Unit>> = _deleteFloorResult

    fun loadFloorDetail(floor: Floor) {
        launchTaskSync(
            onRequest = { floorRepository.getFloorDetail(floor) },
            onSuccess = { _floor.value = it },
            onError = { exception.value = it }
        )
    }

    fun deleteFloor(floor: Floor) {
        launchTaskSync(
            onRequest = { floorRepository.deleteFloor(floor) },
            onSuccess = { _deleteFloorResult.value = DataResult.Success(it) },
            onError = { _deleteFloorResult.value = DataResult.Error(it) }
        )
    }

    fun reset() {
        _deleteFloorResult.value = DataResult.Empty
    }
}