package vn.vngalaxy.fas.presentation.views.sensor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.model.QRDevice
import vn.vngalaxy.fas.presentation.base.BaseViewModel
import vn.vngalaxy.fas.shared.scheduler.DataResult

class QrScanViewModel : BaseViewModel() {
    private val _qrCodeResult = MutableLiveData<DataResult<QRDevice>>()
    val qrCodeResult: LiveData<DataResult<QRDevice>> = _qrCodeResult

    fun getQrData(data: String) {
        runCatching {
            val newDevice = QRDevice.fromJson(data)
            _qrCodeResult.value = DataResult.Success(newDevice)
        }.onFailure {
            _qrCodeResult.value = DataResult.Error(Exception("Qr code không hợp lệ"))
        }
    }

    fun reset() {
        _qrCodeResult.value = DataResult.Empty
    }
}