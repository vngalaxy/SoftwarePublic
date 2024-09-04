package vn.vngalaxy.fas.presentation.views.building

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.BuildingRepository
import vn.vngalaxy.fas.model.Building
import vn.vngalaxy.fas.presentation.base.BaseViewModel

class SelectBuildingViewModel(
    private val buildingRepository: BuildingRepository,
) : BaseViewModel() {
    private val _buildings = MutableLiveData<MutableList<Building>>()
    val buildings: LiveData<MutableList<Building>> = _buildings

    init {
        loadBuildingsLocal()
    }

    private fun loadBuildingsLocal() {
        launchTaskSync(
            onRequest = { buildingRepository.getAllBuildingLocal() },
            onSuccess = { _buildings.value = it.toMutableList() },
            onError = { exception.value = it }
        )
    }
}