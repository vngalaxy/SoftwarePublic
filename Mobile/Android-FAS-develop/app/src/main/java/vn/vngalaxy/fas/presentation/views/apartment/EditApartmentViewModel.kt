package vn.vngalaxy.fas.presentation.views.apartment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.FloorRepository
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.presentation.base.BaseViewModel
import vn.vngalaxy.fas.shared.scheduler.DataResult

class EditApartmentViewModel(
    private val floorRepository: FloorRepository,
) : BaseViewModel() {
    private val _apartment = MutableLiveData<Apartment>()
    val apartment: LiveData<Apartment> = _apartment

    private val _deleteApartmentResult = MutableLiveData<DataResult<Unit>>()
    val deleteApartmentResult: LiveData<DataResult<Unit>> = _deleteApartmentResult

    fun loadApartmentDetail(apartment: Apartment) {
        launchTaskSync(
            onRequest = { floorRepository.getApartmentDetail(apartment) },
            onSuccess = { _apartment.value = it },
            onError = { exception.value = it }
        )
    }

    fun deleteApartment(apartment: Apartment) {
        launchTaskSync(
            onRequest = { floorRepository.deleteApartment(apartment) },
            onSuccess = { _deleteApartmentResult.value = DataResult.Success(it) },
            onError = { _deleteApartmentResult.value = DataResult.Error(it) }
        )
    }

    fun reset() {
        _deleteApartmentResult.value = DataResult.Empty
    }
}