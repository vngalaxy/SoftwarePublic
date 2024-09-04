package vn.vngalaxy.fas.presentation.views.building

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.FloorRepository
import vn.vngalaxy.fas.data.repository.MainBuildingRepository
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.presentation.base.BaseViewModel

class EditBuildingViewModel(
    private val floorRepository: FloorRepository,
    private val building: MainBuildingRepository
) : BaseViewModel() {
    private val _floors = MutableLiveData<List<Floor>>()
    val floors: LiveData<List<Floor>> = _floors

    var buildingName
        get() = building.name
        set(value) {
            building.name = value
        }

    fun loadAllFloors() {
        launchTaskSync(
            onRequest = { floorRepository.getAllFloors() },
            onSuccess = { _floors.value = it },
            onError = { exception.value = it }
        )
    }
}