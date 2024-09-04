package vn.vngalaxy.fas.presentation.views.sensor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.FloorRepository
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.presentation.base.BaseViewModel

class EditSensorViewModel(
    private val floorRepository: FloorRepository,
) : BaseViewModel() {
    private val _floors = MutableLiveData<List<Floor>>()
    val floors: LiveData<List<Floor>> = _floors

    init {
        loadAllFloors()
    }

    fun loadAllFloors() {
        launchTaskSync(
            onRequest = { floorRepository.getAllFloors() },
            onSuccess = { _floors.value = it },
            onError = { exception.value = it }
        )
    }
}