package vn.vngalaxy.fas.presentation.views.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.UserRepository
import vn.vngalaxy.fas.presentation.base.BaseViewModel
import vn.vngalaxy.fas.shared.scheduler.DataResult

class SettingViewModel(
    private val userRepository: UserRepository,
) : BaseViewModel() {
    private val _logoutResult = MutableLiveData<DataResult<Unit>>()
    val logoutResult: LiveData<DataResult<Unit>> = _logoutResult

    fun logout() {
        launchTaskSync(
            onRequest = { userRepository.logout(userRepository.sessionId) },
            onSuccess = { _logoutResult.value = DataResult.Success(it) },
            onError = { _logoutResult.value = DataResult.Error(it) }
        )
    }

    fun reset() {
        _logoutResult.value = DataResult.Empty
    }
}