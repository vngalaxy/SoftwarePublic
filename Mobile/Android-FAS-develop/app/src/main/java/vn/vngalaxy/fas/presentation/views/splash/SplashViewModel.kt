package vn.vngalaxy.fas.presentation.views.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.BuildingRepository
import vn.vngalaxy.fas.data.repository.MainBuildingRepository
import vn.vngalaxy.fas.data.repository.UserRepository
import vn.vngalaxy.fas.data.source.remote.appwrite.AppwriteManager
import vn.vngalaxy.fas.model.Building
import vn.vngalaxy.fas.presentation.base.BaseViewModel
import vn.vngalaxy.fas.shared.constant.Constant.GENERAL_POINT_URL
import vn.vngalaxy.fas.shared.constant.Constant.GENERAL_PROJECT_ID

class SplashViewModel(
    private val buildingRepository: BuildingRepository,
    private val mainBuildingRepository: MainBuildingRepository,
    val user: UserRepository,
    private val appwriteManager: AppwriteManager,
) : BaseViewModel() {
    private val _completedTasks = MutableLiveData<Int>()
    val completedTasks: LiveData<Int> get() = _completedTasks

    init {
        appwriteManager.initClient(GENERAL_POINT_URL, GENERAL_PROJECT_ID)
        _completedTasks.value = 0
        loadBuildings()
    }

    private fun loadBuildings() {
        launchTaskSync(
            onRequest = { buildingRepository.getAllBuilding() },
            onSuccess = {
                if (it.isNotEmpty()) {
                    insertMultiBuildings(it)
                }

                incrementCompletedTasks()
            },
            onError = { exception.value = it }
        )
    }

    private fun insertMultiBuildings(buildings: List<Building>) {
        launchTaskSync(
            onRequest = { buildingRepository.insertMultiBuildingsLocal(buildings) },
            onSuccess = { incrementCompletedTasks() },
            onError = { exception.value = it }
        )
    }

    fun initSubDatabase() {
        appwriteManager.initClient(mainBuildingRepository.endPointUrl, mainBuildingRepository.projectId)
    }

    private fun incrementCompletedTasks() {
        _completedTasks.value = (_completedTasks.value ?: 0) + 1
    }

    companion object {
        const val TOTAL_TASK = 2
    }
}