package vn.vngalaxy.fas.presentation.views.sensor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.appwrite.models.RealtimeSubscription
import vn.vngalaxy.fas.data.repository.MainBuildingRepository
import vn.vngalaxy.fas.data.repository.RoomRepository
import vn.vngalaxy.fas.data.source.remote.appwrite.AppwriteManager
import vn.vngalaxy.fas.model.Room
import vn.vngalaxy.fas.model.Sensor
import vn.vngalaxy.fas.presentation.base.BaseViewModel

class SensorDetailViewModel(
    private val appwrite: AppwriteManager,
    private val building: MainBuildingRepository,
    private val roomRepository: RoomRepository,
) : BaseViewModel() {
    private lateinit var subscriptionSensor: RealtimeSubscription

    private val _realTimeSensor = MutableLiveData<Sensor>()
    val realTimeSensor: LiveData<Sensor> = _realTimeSensor

    private val _room = MutableLiveData<Room>()
    val room: LiveData<Room> = _room

    fun loadSensorDataRealtime(sensor: Sensor) {
        val channel = "databases.${building.buildingDatabaseId}.collections.${building.sensorCollectionId}.documents.${sensor.id}"
        subscriptionSensor = appwrite.getRealtime().subscribe(channel) {
            _realTimeSensor.postValue(Sensor.fromMap(it.payload as Map<String, Any>))
        }
    }

    fun loadRoomsDetail(room: Room) {
        launchTaskSync(
            onRequest = { roomRepository.getRoomDetail(room) },
            onSuccess = { _room.value = it },
            onError = { exception.value = it }
        )
    }

    private fun unsubscribe() {
        subscriptionSensor.close()
    }

    override fun onCleared() {
        super.onCleared()
        unsubscribe()
    }
}