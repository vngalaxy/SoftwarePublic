package vn.vngalaxy.fas.presentation.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.vngalaxy.fas.data.repository.MainBuildingRepository
import vn.vngalaxy.fas.data.repository.UserRepository
import vn.vngalaxy.fas.data.source.remote.appwrite.AppwriteManager
import vn.vngalaxy.fas.model.Building
import vn.vngalaxy.fas.presentation.base.BaseViewModel
import vn.vngalaxy.fas.shared.constant.Constant.PRIMARY_COUNTRY_CODE
import vn.vngalaxy.fas.shared.scheduler.DataResult

class LoginViewModel(
    private val userRepository: UserRepository,
    private val mainBuildingRepository: MainBuildingRepository,
    private val appwriteManager: AppwriteManager,
) : BaseViewModel() {
    private val _isUserExists = MutableLiveData<DataResult<Boolean>>()
    val isUserExists: LiveData<DataResult<Boolean>> = _isUserExists

    private val _createPhoneTokenResult = MutableLiveData<DataResult<Boolean>>()
    val createPhoneTokenResult: LiveData<DataResult<Boolean>> = _createPhoneTokenResult

    fun saveMainBuilding(building: Building, phoneNumber: String) {
        val phoneWithCode = PRIMARY_COUNTRY_CODE.plus(phoneNumber)
        viewModelScope.launch {
            runCatching {
                mainBuildingRepository.projectId = building.projectId.toString()
                mainBuildingRepository.name = building.name.toString()
                mainBuildingRepository.endPointUrl = building.endPointUrl.toString()
                mainBuildingRepository.buildingDatabaseId = building.buildingDatabaseId.toString()
                mainBuildingRepository.userCollectionId = building.userCollectionId.toString()
                mainBuildingRepository.floorCollectionId = building.floorCollectionId.toString()
                mainBuildingRepository.apartmentCollectionId = building.apartmentCollectionId.toString()
                mainBuildingRepository.roomCollectionId = building.roomCollectionId.toString()
                mainBuildingRepository.sensorCollectionId = building.sensorCollectionId.toString()
                mainBuildingRepository.userNotificationCollectionId = building.userNotificationCollectionId.toString()
                mainBuildingRepository.sensorNotificationCollectionId = building.sensorNotificationCollectionId.toString()
                mainBuildingRepository.notificationCollectionId = building.notificationCollectionId.toString()
                mainBuildingRepository.caseFireLocationCollectionId = building.caseFireLocationCollectionId.toString()
                if (building.cornerLocationBuilding != null) {
                    mainBuildingRepository.cornerLocationBuilding = building.cornerLocationBuilding
                }
            }.onSuccess {
                appwriteManager.initClient(mainBuildingRepository.endPointUrl, mainBuildingRepository.projectId)
                userRepository.phoneNumber = phoneWithCode
                checkUserExists(phoneNumber)
            }
        }
    }

    private fun checkUserExists(phoneNumber: String) {
        val phoneWithCode = PRIMARY_COUNTRY_CODE.plus(phoneNumber)
        launchTaskSync(
            onRequest = { userRepository.checkUserExists(phoneWithCode) },
            onSuccess = { _isUserExists.value = DataResult.Success(it) },
            onError = { _isUserExists.value = DataResult.Error(it) }
        )
    }

    fun createPhoneToken(phoneNumber: String) {
        val phoneWithCode = PRIMARY_COUNTRY_CODE.plus(phoneNumber)
        launchTaskSync(
            onRequest = { userRepository.createPhoneToken(phoneWithCode) },
            onSuccess = {
                userRepository.id = it
                _createPhoneTokenResult.value = DataResult.Success(true)
            },
            onError = { _createPhoneTokenResult.value = DataResult.Error(it) }
        )
    }

    fun reset() {
        _isUserExists.value = DataResult.Empty
        _createPhoneTokenResult.value = DataResult.Empty
    }
}