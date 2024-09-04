package vn.vngalaxy.fas.presentation.views.sensor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.vngalaxy.fas.data.repository.FloorRepository
import vn.vngalaxy.fas.data.repository.SensorRepository
import vn.vngalaxy.fas.model.Apartment
import vn.vngalaxy.fas.model.ChirpStackDevice
import vn.vngalaxy.fas.model.ChirpStackDeviceKeys
import vn.vngalaxy.fas.model.Floor
import vn.vngalaxy.fas.model.QRDevice
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.model.Sensor
import vn.vngalaxy.fas.model.SensorStatus
import vn.vngalaxy.fas.model.SensorType
import vn.vngalaxy.fas.presentation.base.BaseViewModel
import vn.vngalaxy.fas.shared.scheduler.DataResult

class AddSensorViewModel(
    private val floorRepository: FloorRepository,
    private val sensorRepository: SensorRepository,
) : BaseViewModel() {
    private val _floors = MutableLiveData<List<Floor>>()
    val floors: LiveData<List<Floor>> = _floors

    //    private val _selectedType = MutableLiveData<String>()
//    val selectedType: LiveData<String> = _selectedType
    private val _selectedFloor = MutableLiveData<Floor>()
    val selectedFloor: LiveData<Floor> = _selectedFloor
    private val _selectedApartment = MutableLiveData<Apartment>()
    val selectedApartment: LiveData<Apartment> = _selectedApartment
    private val _selectedRoom = MutableLiveData<Room>()
    val selectedRoom: LiveData<Room> = _selectedRoom

//    var sensorId = ""
//    var sensorName = ""

    private val _addSensorResult = MutableLiveData<DataResult<Unit>>()
    val addSensorResult: LiveData<DataResult<Unit>> = _addSensorResult

//    fun setSelectedType(type: String) {
//        _selectedType.value = type
//    }

    fun setSelectedFloor(floor: Floor) {
        _selectedFloor.value = floor
    }

    fun setSelectedApartment(apartment: Apartment) {
        _selectedApartment.value = apartment
    }

    fun setSelectedRoom(room: Room) {
        _selectedRoom.value = room.copy(apartment = _selectedApartment.value?.copy(floor = _selectedFloor.value))
    }

    init {
        loadAllFloors()
    }

    private fun loadAllFloors() {
        launchTaskSync(
            onRequest = { floorRepository.getAllFloors() },
            onSuccess = { _floors.value = it },
            onError = { exception.value = it }
        )
    }

    fun addSensor(qrDevice: QRDevice) {
        val sensor = Sensor(
            id = qrDevice.devEUI,
            name = qrDevice.sensorName,
            type = SensorType.fromId(qrDevice.deviceProfileID).value,
            status = SensorStatus.ON.value,
            room = _selectedRoom.value
        )
        val chirpStackDevice = ChirpStackDevice(
            applicationID = qrDevice.applicationID,
            description = "${sensor.room?.apartment?.floor?.name} ${sensor.room?.apartment?.name} ${sensor.room?.name}",
            devEUI = qrDevice.devEUI,
            deviceProfileID = qrDevice.deviceProfileID,
            name = qrDevice.sensorName,
        )
        val chirpStackDeviceKeys = ChirpStackDeviceKeys(
            devEUI = qrDevice.devEUI,
            nwkKey = qrDevice.nwkKey
        )

        launchTaskSync(
            onRequest = { sensorRepository.addSensor(sensor, chirpStackDevice, chirpStackDeviceKeys) },
            onSuccess = { _addSensorResult.value = DataResult.Success(it) },
            onError = { _addSensorResult.value = DataResult.Error(it) }
        )
    }

    fun reset() {
        _addSensorResult.value = DataResult.Empty
    }
}