package vn.vngalaxy.fas.presentation.views.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import vn.vngalaxy.fas.data.repository.UserRepository
import vn.vngalaxy.fas.data.source.remote.appwrite.AppwriteManager
import vn.vngalaxy.fas.presentation.base.BaseViewModel
import vn.vngalaxy.fas.shared.scheduler.DataResult

class OtpViewModel(
    val user: UserRepository,
    private val appwriteManager: AppwriteManager,
) : BaseViewModel() {
    private val _createSessionResult = MutableLiveData<DataResult<Boolean>>()
    val createSessionResult: LiveData<DataResult<Boolean>> = _createSessionResult

    fun createSession(secret: String) {
        launchTaskSync(
            onRequest = { user.createSession(secret) },
            onSuccess = { saveUserValue(it) },
            onError = { _createSessionResult.value = DataResult.Error(it) }
        )
    }

    private fun saveUserValue(sessionId: String) {
        viewModelScope.launch {
            runCatching {
                val label = appwriteManager.getAccount().get().labels.first().toString()
                val token = FirebaseMessaging.getInstance().token.await()

                user.saveUserInfo(
                    userId = user.id,
                    role = label,
                    sessionId = sessionId,
                    token = token,
                    isLogin = true,
                )
            }.onSuccess {
                _createSessionResult.value = DataResult.Success(true)
            }.onFailure {
                _createSessionResult.value = DataResult.Error(Exception(it))
            }
        }
    }

    fun reset() {
        _createSessionResult.value = DataResult.Empty
    }
}