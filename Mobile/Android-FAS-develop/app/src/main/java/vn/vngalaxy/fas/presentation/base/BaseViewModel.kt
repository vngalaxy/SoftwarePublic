package vn.vngalaxy.fas.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import vn.vngalaxy.fas.shared.scheduler.DataResult

abstract class BaseViewModel : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val isSuccess = MutableLiveData<Boolean>()
    val exception = MutableLiveData<Exception>()

    protected fun <T> launchTaskSync(
        onRequest: suspend CoroutineScope.() -> DataResult<T>,
        onSuccess: (T) -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) = viewModelScope.launch {
        isLoading.postValue(true)

        when (val result = onRequest()) {
            is DataResult.Success -> {
                isSuccess.postValue(true)
                onSuccess(result.data)
            }

            is DataResult.Error -> {
                isSuccess.postValue(false)
                onError(result.exception)
            }

            is DataResult.Loading -> {}

            is DataResult.Empty -> {}
        }

        isLoading.postValue(false)
    }
}
